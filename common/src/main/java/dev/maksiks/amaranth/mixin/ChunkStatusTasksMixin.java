package dev.maksiks.amaranth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.maksiks.amaranth.worldgen.biome.ModBiomes;
import dev.maksiks.amaranth.worldgen.biome.terrain.MindlessRoseryTerrain;
import dev.maksiks.amaranth.worldgen.biome.terrain.MushlandTerrain;
import dev.maksiks.amaranth.worldgen.biome.terrain.SteppedSpringsTerrain;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatusTasks;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Mixin(ChunkStatusTasks.class)
public abstract class ChunkStatusTasksMixin {
    @Inject(method = "generateSurface", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkGenerator;buildSurface(Lnet/minecraft/server/level/WorldGenRegion;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;)V"))
    private static void injectBiomeTerrain(WorldGenContext worldGenContext, ChunkStep step, StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir, @Local ServerLevel serverLevel, @Local WorldGenRegion worldGenRegion) {
        BiomeManager biomeManager = worldGenRegion.getBiomeManager()
                .withDifferentSource((x, y, z) -> worldGenContext.generator().getBiomeSource().getNoiseBiome(x, y, z, worldGenContext.level()
                        .getChunkSource().randomState().sampler()));
        Long2ObjectOpenHashMap<Long2ObjectOpenHashMap<Holder<Biome>>> biomeCache = new Long2ObjectOpenHashMap<>();

        Function<BlockPos, Holder<Biome>> biomeGetter =
                pos -> biomeCache.computeIfAbsent(ChunkPos.asLong(pos),
                        key0 -> new Long2ObjectOpenHashMap<>()).computeIfAbsent(ChunkPos.asLong(pos.getX(), pos.getZ()),
                        key1 -> biomeManager.getBiome(pos));

        // biomes
        MushlandTerrain.process(biomeGetter, chunk);

        SteppedSpringsTerrain.process(biomeGetter, chunk, worldGenRegion);


    }

    // super duper bootleg, this is not a feature but i went down the rabbit hole of asynchronously storing
    // the chunks to access nearby chunks for smoothing for this to no avail and no thank u this is fine enough
    //
    // im frankly just kind gambler's fallacy doing this for my own knowledge but im realizing
    // i'm not getting anywhere if i don't do more different stuff with chunks or else i'll spend
    // way too much time on this, and i mean i already spent way too much time on this.
    // so this is the best thing i can come up with
    @Inject(
            method = "generateFeatures",
            at = @At("HEAD")
    )
    private static void roseryTerrain(
            WorldGenContext worldGenContext, ChunkStep step,
            StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk,
            CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir
    ) {
        ServerLevel serverLevel = worldGenContext.level();
        WorldGenRegion worldGenRegion = new WorldGenRegion(serverLevel, cache, step, chunk);

        BiomeManager biomeManager = worldGenRegion.getBiomeManager()
                .withDifferentSource((x, y, z) -> worldGenContext.generator().getBiomeSource().getNoiseBiome(x, y, z, worldGenContext.level()
                        .getChunkSource().randomState().sampler()));
        Long2ObjectOpenHashMap<Long2ObjectOpenHashMap<Holder<Biome>>> biomeCache = new Long2ObjectOpenHashMap<>();

        Function<BlockPos, Holder<Biome>> biomeGetter =
                pos -> biomeCache.computeIfAbsent(ChunkPos.asLong(pos),
                        key0 -> new Long2ObjectOpenHashMap<>()).computeIfAbsent(ChunkPos.asLong(pos.getX(), pos.getZ()),
                        key1 -> biomeManager.getBiome(pos));

        boolean hasRosery = false;
        outer:
        for (int bx = 0; bx < 16; bx += 4) {
            for (int bz = 0; bz < 16; bz += 4) {
                BlockPos probe = new BlockPos(chunk.getPos().getMinBlockX() + bx, 64, chunk.getPos().getMinBlockZ() + bz);
                if (biomeGetter.apply(probe).is(ModBiomes.MINDLESS_ROSERY)) {
                    hasRosery = true;
                    break outer;
                }
            }
        }

        if (!hasRosery) return;

        // making terrain first on top of vanilla
        MindlessRoseryTerrain.process(biomeGetter, chunk, worldGenRegion);
        // then remaking the heightmaps and re-applying the surface rules
        MindlessRoseryTerrain.reapplySurface(worldGenContext, worldGenRegion, serverLevel, chunk);
    }

    @Inject(
            method = "full",
            at = @At("TAIL")
    )
    private static void fillBoulderStone(
            WorldGenContext worldGenContext, ChunkStep step,
            StaticCache2D<GenerationChunkHolder> cache, ChunkAccess chunk,
            CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir
    ) {
        MindlessRoseryTerrain.fillBoulderStone(chunk);
    }
}