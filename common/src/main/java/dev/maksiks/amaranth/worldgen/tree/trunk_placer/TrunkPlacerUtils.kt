package dev.maksiks.amaranth.worldgen.tree.trunk_placer

import dev.maksiks.amaranth.worldgen.tree.TreeUtils.isAdjacent
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.TreeFeature
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import java.util.function.BiConsumer
import java.util.function.Function

object TrunkPlacerUtils {
    @JvmStatic
    fun placeLogOnAxis(
        dir: Direction,
        level: LevelSimulatedReader,
        blockSetter: BiConsumer<BlockPos?, BlockState?>,
        random: RandomSource,
        pos: BlockPos,
        config: TreeConfiguration,
        propertySetter: Function<BlockState?, BlockState?>
    ): Boolean {
        if (TreeFeature.validTreePos(level, pos)) {
            blockSetter.accept(
                pos,
                propertySetter.apply(
                    config.trunkProvider.getState(random, pos)
                        .setValue<Direction.Axis?, Direction.Axis?>(RotatedPillarBlock.AXIS, dir.axis)
                )
            )
            return true
        } else {
            return false
        }
    }


    @JvmStatic
    fun hasLogAround(pos: BlockPos, level: LevelSimulatedReader): Boolean {
        for (dir in Direction.Plane.HORIZONTAL) {
            if (level.isStateAtPosition(pos.relative(dir)) { it.`is`(BlockTags.LOGS) }) return true
        }

        return false
    }
}
