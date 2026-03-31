package dev.maksiks.amaranth.worldgen.biome.terrain

import dev.maksiks.amaranth.block.ModBlocks
import dev.maksiks.amaranth.worldgen.biome.ModBiomes
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.server.level.WorldGenRegion
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.levelgen.synth.PerlinNoise
import java.util.function.Function
import java.util.stream.IntStream
import kotlin.math.pow

/**
 *
 * This is one kinda jankier and does nothing in the mountains
 * as it is more complex than the last ones
 * and im actually using noise rather than cutting out or adding blocks
 *
 */

// TODO now: optimize
// TODO now: flatten vanilla bits
class MindlessRoseryTerrain {
    companion object {
        private const val BASE_Y = 63
        private const val MAX_HEIGHT = 20

        private const val FREQUENCY = 0.025
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

            for (x in 0..15) {
                for (z in 0..15) {
                    val worldX = startX + x
                    val worldZ = startZ + z

                    val pos = BlockPos(worldX, 64, worldZ) // temp Y for biome check
                    val biome = biomeGetter.apply(pos)

                    if (!biome.`is`(ModBiomes.MINDLESS_ROSERY)) continue

                    val isWaterColumn = region.getBlockState(BlockPos(worldX, BASE_Y - 1, worldZ)).`is`(Blocks.WATER)

                    var inBiome = 0
                    var total = 0
                    for (bx in -BLEND_RADIUS..BLEND_RADIUS) {
                        for (bz in -BLEND_RADIUS..BLEND_RADIUS) {
                            if (bx * bx + bz * bz > BLEND_RADIUS * BLEND_RADIUS) continue
                            total++
                            val checkPos = BlockPos(worldX + bx, 64, worldZ + bz)
                            val isInBiome = biomeGetter.apply(checkPos).`is`(ModBiomes.MINDLESS_ROSERY)
                            val isWater = region.getBlockState(BlockPos(worldX + bx, BASE_Y - 1, worldZ + bz)).`is`(Blocks.WATER)
                            if (isInBiome && !isWater) inBiome++
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
    }
}