package dev.maksiks.amaranth.worldgen.tree.trunk_placer.custom_placer

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.maksiks.amaranth.block.ModBlocks
import dev.maksiks.amaranth.worldgen.tree.TreeUtils
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.ModTrunkPlacerTypes
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.TrunkPlacerUtils
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.TrunkPlacerUtils.placeLogOnAxis
import dev.maksiks.twigonometry.api.WildcardFoliageAttachment
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType
import java.util.*
import java.util.Map
import java.util.function.BiConsumer
import java.util.function.Function

class FrontierBlossomTrunkPlacer(baseHeight: Int, heightRandA: Int, heightRandB: Int) :
    TrunkPlacer(baseHeight, heightRandA, heightRandB) {
    companion object {
        @JvmField
        var CODEC: MapCodec<FrontierBlossomTrunkPlacer?> = RecordCodecBuilder.mapCodec<FrontierBlossomTrunkPlacer?>(
            Function { instance: RecordCodecBuilder.Instance<FrontierBlossomTrunkPlacer?>? ->
                trunkPlacerParts<FrontierBlossomTrunkPlacer?>(
                    instance!!
                ).apply<FrontierBlossomTrunkPlacer?>(
                    instance
                ) { baseHeight: Int?, heightRandA: Int?, heightRandB: Int? ->
                    FrontierBlossomTrunkPlacer(
                        baseHeight!!,
                        heightRandA!!,
                        heightRandB!!
                    )
                }
            }
        )
    }

    override fun type(): TrunkPlacerType<*> {
        return ModTrunkPlacerTypes.FRONTIER_BLOSSOM_TRUNK_PLACER.get()
    }

    var maxCressetCount = -1
    var cressetCount = 0

    // override for cressets
    override fun placeLog(
        level: LevelSimulatedReader,
        blockSetter: BiConsumer<BlockPos?, BlockState?>,
        random: RandomSource,
        pos: BlockPos,
        config: TreeConfiguration
    ): Boolean {
        val below = pos.below();
        if (cressetCount < maxCressetCount
            && random.nextInt(100) < 100
            && level.isStateAtPosition(below) { it.`is`(BlockTags.AIR)}) {
            blockSetter.accept(pos.below(), ModBlocks.CRESSET_FLOWER.get()?.defaultBlockState())
            cressetCount++
        }
        return super.placeLog(level, blockSetter, random, pos, config)
    }

    internal enum class Variant {
        BUNS,
        DROOPY
    }

    override fun placeTrunk(
        level: LevelSimulatedReader,
        blockSetter: BiConsumer<BlockPos?, BlockState?>,
        random: RandomSource,
        freeTreeHeight: Int,
        pos: BlockPos,
        config: TreeConfiguration
    ): MutableList<FoliagePlacer.FoliageAttachment?> {
        setDirtAt(level, blockSetter, random, pos.below(), config)

        val attachments: MutableList<FoliagePlacer.FoliageAttachment?> = ArrayList<FoliagePlacer.FoliageAttachment?>()
        val variant = if (random.nextBoolean()) Variant.BUNS else Variant.DROOPY
        maxCressetCount = if (RandomSource.create().nextBoolean()) 2 else 3
        cressetCount = 0
        var diagBranchCount = if (variant == Variant.DROOPY)
            random.nextInt(2) else random.nextInt(3)
        if (random.nextBoolean()) diagBranchCount--
        diagBranchCount = diagBranchCount.coerceAtLeast(0)

        val maxRandomBranches = if (variant == Variant.DROOPY) 2 else 3
        var branchCount = random.nextInt(maxRandomBranches) + 2 + diagBranchCount

        val trunkHeight = random.nextInt(3) + 3

        // trunk
        repeat (trunkHeight) { i ->
            this.placeLog(level, blockSetter, random, pos.above(i), config)
        }

        // branches
        val minBranchY = 2

        val possibleSlots = (minBranchY until trunkHeight).flatMap { y ->
            Direction.Plane.HORIZONTAL.stream().toList().map { dir -> Pair(y, dir) }
        }.shuffled(Random())

        // could also check for adjacency, but this already took a while

        var slotIndex = 0
        var highTaken = false
        val occupiedTrunkYs = mutableSetOf<Int>()
        while (branchCount > 0 && slotIndex < possibleSlots.size) {
            val (y, dir) = possibleSlots[slotIndex++]
            occupiedTrunkYs.add(y)
            val diagonal = diagBranchCount > 0

            branchCount--
            if (diagonal) diagBranchCount--

            var branchLength = random.nextInt(if (highTaken) 1 else 2) + 2
            if (branchLength == 3 && random.nextBoolean()) {
                highTaken = true
                branchLength--
            }
            val squiggly = random.nextBoolean()

            if (branchLength == 2 && diagonal) {
                val xwisedir = TreeUtils.getRandomXWiseDir(dir, random);

                val diagPos = pos.above(y).relative(dir).relative(xwisedir)
                val tine = diagPos.above()
                    .relative(dir).relative(xwisedir)

                placeLogOnAxis(dir, level, blockSetter, random, diagPos,
                    config, propertySetter = Function.identity())
                this.placeLog(level, blockSetter, random, tine, config)

                attachments.add(WildcardFoliageAttachment(tine, 0, false, Map.entry("variant", variant), Map.entry("dir", dir)))
            }
            if (branchLength == 2 && !diagonal) {
                placeLogOnAxis(dir, level, blockSetter, random, pos.above(y).relative(dir),
                    config, propertySetter = Function.identity())

                var tine: BlockPos

                if (squiggly) {
                    val xwisedir = TreeUtils.getRandomXWiseDir(dir, random);
                    tine = pos.above(y + 1)
                        .relative(dir).relative(dir).relative(xwisedir)

                    this.placeLog(level, blockSetter, random, tine, config)
                } else {
                    tine = pos.above(y + 1)
                        .relative(dir, 2)

                    this.placeLog(level, blockSetter, random, tine, config)
                }

                attachments.add(WildcardFoliageAttachment(tine, 0, false, Map.entry("variant", variant), Map.entry("dir", dir)))
            }
            if (branchLength == 3) {
                val tine = pos.above(y + 2).relative(dir, 2)

                this.placeLog(level, blockSetter, random, pos.above(y).relative(dir), config)
                this.placeLog(level, blockSetter, random, pos.above(y + 1).relative(dir), config)
                this.placeLog(level, blockSetter, random, tine, config)

                attachments.add(WildcardFoliageAttachment(tine, 0, false, Map.entry("variant", variant), Map.entry("dir", dir)))
            }
        }

        // culling tines without branches
        for (i in trunkHeight - 1 downTo 3) {
            val logSegmentPos = pos.above(i)
            val isCurTine = !level.isStateAtPosition(logSegmentPos.above()) { it.`is`(BlockTags.LOGS) }
            if (i !in occupiedTrunkYs && isCurTine) {
                blockSetter.accept(logSegmentPos, Blocks.AIR.defaultBlockState())
            }
        }
        return attachments
    }
}
