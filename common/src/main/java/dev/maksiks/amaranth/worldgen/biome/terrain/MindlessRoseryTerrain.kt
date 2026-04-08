package dev.maksiks.amaranth.worldgen.biome.terrain

import dev.maksiks.amaranth.worldgen.biome.ModBiomes
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.WorldGenRegion
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.chunk.status.WorldGenContext
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.synth.PerlinNoise
import java.util.*
import java.util.function.Function
import java.util.stream.IntStream
import kotlin.math.pow

// TODO: remake when wise

/**
 *
 * This is one kinda jankier as it is more complex than the last ones
 * and im actually using noise rather than cutting out or adding blocks
 * me brain fry
 *
 * this should've really been more like a vanilla terrain smoother, but i just
 * wanted to play around with noise lol
 * ^ edit: and that 100% made it look better in the low parts tbh
 * ^ edit: ok, this whole biome processor has been a disaster in development,
 * my current approach for this sucks and this is
 * mostly because i started it without fully understanding how chunks really work,
 * this is fine-ish for now but definitely needs a remake to not be stuck in
 * with features, this is also not 100% reliable and might cause minor
 * blending inconsistencies between seeds due to lag which i really dont like,
 * but it's also at the edge where i'd feel bad scrapping it because
 * only a few people would ever notice
 *
 */

class MindlessRoseryTerrain {
    companion object {
        private const val BASE_Y = 63
        private const val MAX_HEIGHT = 20

        private const val FREQUENCY = 0.025

        // i think i stupided the math and these two do nothing or very minor somehow but it fine
        private const val OCTAVES = 2
        private const val AMPLITUDE = 0.2

        private const val BLEND_RADIUS = 16
        private const val BLEND_STRENGTH = 3.0

        private val noise = PerlinNoise.create(
            RandomSource.create(42342L),
            IntStream.rangeClosed(-OCTAVES + 1, 0).boxed().toList()
        )

        @JvmStatic
        fun process(
            biomeGetter: Function<BlockPos, Holder<Biome>>,
            chunk: ChunkAccess,
            region: WorldGenRegion
        ) {
            val chunkPos = chunk.pos
            val startX = chunkPos.minBlockX
            val startZ = chunkPos.minBlockZ

            val cacheSize = 16 + BLEND_RADIUS * 2
            val cacheOffset = BLEND_RADIUS

            val blendCache = Array(cacheSize) { bx ->
                BooleanArray(cacheSize) { bz ->
                    val wx = startX - BLEND_RADIUS + bx
                    val wz = startZ - BLEND_RADIUS + bz
                    val isInBiome = biomeGetter.apply(BlockPos(wx, 64, wz)).`is`(ModBiomes.MINDLESS_ROSERY)
                    val neighborChunk = region.getChunk(wx shr 4, wz shr 4)
                    val lx = wx and 15
                    val lz = wz and 15
                    val isWater = neighborChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG).getFirstAvailable(lx, lz) !=
                            neighborChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG).getFirstAvailable(lx, lz)
                    isInBiome && !isWater
                }
            }

            // custom terrain
            for (x in 0..15) {
                for (z in 0..15) {
                    val worldX = startX + x
                    val worldZ = startZ + z

                    if (!biomeGetter.apply(BlockPos(worldX, 64, worldZ)).`is`(ModBiomes.MINDLESS_ROSERY)) continue

                    val isWaterColumn = region.getBlockState(BlockPos(worldX, BASE_Y - 1, worldZ)).`is`(Blocks.WATER)

                    // skipping placing if it's higher than my plain anyway as an optimization
                    val heightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG)
                    if (heightmap.getFirstAvailable(x, z) > BASE_Y + MAX_HEIGHT) continue

                    var inBiome = 0
                    var total = 0
                    for (bx in -BLEND_RADIUS..BLEND_RADIUS) {
                        for (bz in -BLEND_RADIUS..BLEND_RADIUS) {
                            if (bx * bx + bz * bz > BLEND_RADIUS * BLEND_RADIUS) continue
                            total++
                            if (blendCache[x + cacheOffset + bx][z + cacheOffset + bz]) inBiome++
                        }
                    }
                    val blendFactor = inBiome.toDouble() / total

                    val noiseVal = noise.getValue(worldX * FREQUENCY, 0.0, worldZ * FREQUENCY) * AMPLITUDE
                    val height =
                        ((noiseVal / AMPLITUDE + 1.0) / 2.0 * MAX_HEIGHT * blendFactor.pow(BLEND_STRENGTH)).toInt()
                            .coerceIn(0, MAX_HEIGHT)
                    val topY = (BASE_Y - 1) + height

                    if (isWaterColumn) continue

                    for (y in BASE_Y..topY) {
                        chunk.setBlockState(BlockPos(worldX, y, worldZ), Blocks.STONE.defaultBlockState(), false)
                    }
                }
            }

            // smoothing
            smoothTerrain(biomeGetter, chunk)
        }

        @JvmStatic
        fun smoothTerrain(biomeGetter: Function<BlockPos, Holder<Biome>>, chunk: ChunkAccess) {
            val chunkPos = chunk.pos
            val startX = chunkPos.minBlockX
            val startZ = chunkPos.minBlockZ

            val heightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG)

            for (x in 0..15) {
                for (z in 0..15) {
                    val worldX = startX + x
                    val worldZ = startZ + z

                    if (!biomeGetter.apply(BlockPos(worldX, 64, worldZ)).`is`(ModBiomes.MINDLESS_ROSERY)) continue

                    val topY = heightmap.getFirstAvailable(x, z) - 1

                    for (y in BASE_Y..topY) {
                        val state = chunk.getBlockState(BlockPos(worldX, y, worldZ))
                        if (state.isAir || state.`is`(Blocks.WATER)) continue

                        var neighbours = 0
                        for (bx in -4..4) {
                            for (bz in -4..4) {
                                val nx = x + bx
                                val nz = z + bz
                                // clamping to the edges of the chunk to prevent skill issue when a neighbor is not yet loaded
                                if (nx !in 0..15 || nz !in 0..15) continue
                                val nState = chunk.getBlockState(BlockPos(worldX + bx, y, worldZ + bz))
                                if (!nState.isAir && !nState.`is`(Blocks.WATER)) neighbours++
                            }
                        }

                        val checked = ((-4..4).sumOf { bx -> (-4..4).count { bz -> (x+bx) in 0..15 && (z+bz) in 0..15 } })
                        val threshold = (16.0 / 81.0 * checked).toInt()

                        if (neighbours < threshold) {
                            chunk.setBlockState(BlockPos(worldX, y, worldZ), Blocks.AIR.defaultBlockState(), false)
                        }
                    }
                }
            }
        }

        @JvmStatic
        fun reapplySurface(
            ctx: WorldGenContext,
            region: WorldGenRegion,
            serverLevel: ServerLevel,
            chunk: ChunkAccess
        ) {
            Heightmap.primeHeightmaps(
                chunk, EnumSet.of(
                    Heightmap.Types.WORLD_SURFACE_WG,
                    Heightmap.Types.OCEAN_FLOOR_WG,
                    Heightmap.Types.WORLD_SURFACE,
                    Heightmap.Types.MOTION_BLOCKING
                )
            )

            ctx.generator().buildSurface(
                region,
                serverLevel.structureManager().forWorldGenRegion(region),
                serverLevel.chunkSource.randomState(),
                chunk
            )
        }
    }
}