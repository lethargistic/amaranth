package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.particle.ModParticles;
import dev.maksiks.amaranth.worldgen.tree.foliage_placer.ModFoliagePlacerTypes;
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.ModTrunkPlacerTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoModRegistries {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, Constants.MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Constants.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        ModBlocks.BLOCK_MAP.forEach(BLOCKS::register);
        ModItems.ITEM_MAP.forEach(ITEMS::register);
        ModEntities.ENTITY_TYPE_MAP.forEach(ENTITY_TYPES::register);
        ModTrunkPlacerTypes.TRUNK_PLACER_TYPE_MAP.forEach(TRUNK_PLACER_TYPES::register);
        ModFoliagePlacerTypes.FOLIAGE_PLACER_TYPE_MAP.forEach(FOLIAGE_PLACER_TYPES::register);
        ModParticles.PARTICLE_TYPE_MAP.forEach(PARTICLE_TYPES::register);

        ENTITY_TYPES.register(eventBus);
        TRUNK_PLACER_TYPES.register(eventBus);
        FOLIAGE_PLACER_TYPES.register(eventBus);
        PARTICLE_TYPES.register(eventBus);
    }
}
