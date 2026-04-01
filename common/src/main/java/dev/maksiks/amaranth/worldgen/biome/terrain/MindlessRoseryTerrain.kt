package dev.maksiks.amaranth.worldgen.biome.terrain

import dev.maksiks.amaranth.worldgen.biome.ModBiomes
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.server.level.WorldGenRegion
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.synth.PerlinNoise
import java.util.function.Function
import java.util.stream.IntStream
import kotlin.math.pow

/**
 *
 * This is one kinda jankier as it is more complex than the last ones
 * and im actually using noise rather than cutting out or adding blocks
 * me brain fry
 *
 * this should've really been more like a vanilla terrain smoother, but i just
 * wanted to play around with noise lol
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
                    val isWater = region.getBlockState(BlockPos(wx, BASE_Y - 1, wz)).`is`(Blocks.WATER)
                    isInBiome && !isWater
                }
            }

            // smoothing
            smoothTerrain(biomeGetter, chunk, region)

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
                    val height = ((noiseVal / AMPLITUDE + 1.0) / 2.0 * MAX_HEIGHT * blendFactor.pow(BLEND_STRENGTH)).toInt().coerceIn(0, MAX_HEIGHT)
                    val topY = (BASE_Y - 1) + height

                    if (isWaterColumn) continue

                    for (y in BASE_Y..topY) {
                        chunk.setBlockState(BlockPos(worldX, y, worldZ), Blocks.STONE.defaultBlockState(), false)
                    }
                }
            }
        }

        @JvmStatic
        fun smoothTerrain(biomeGetter: Function<BlockPos, Holder<Biome>>, chunk: ChunkAccess, region: WorldGenRegion) {
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
                        val state = chunk.getBlockState(BlockPos(x, y, z))
                        if (state.isAir || state.`is`(Blocks.WATER)) continue

                        var neighbours = 0
                        for (bx in -4..4) {
                            for (bz in -4..4) {
                                val nState = region.getBlockState(BlockPos(worldX + bx, y, worldZ + bz))
                                if (!nState.isAir && !nState.`is`(Blocks.WATER)) neighbours++
                            }
                        }

                        if (neighbours < 16) {
                            chunk.setBlockState(BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), false)
                        }
                    }
                }
            }
        }    }
}