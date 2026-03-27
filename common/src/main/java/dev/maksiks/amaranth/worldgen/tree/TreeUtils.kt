package dev.maksiks.amaranth.worldgen.tree

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import kotlin.math.abs

object TreeUtils {
    /**
     * checks if two positions are adjacent within 1 block in any direction, including diagonals
     */
    @JvmStatic
    fun isAdjacent(pos1: BlockPos, pos2: BlockPos): Boolean {
        if (pos1 == pos2) {
            return false
        }

        val deltaX = abs(pos1.x - pos2.x)
        val deltaY = abs(pos1.y - pos2.y)
        val deltaZ = abs(pos1.z - pos2.z)

        return deltaX <= 1 && deltaY <= 1 && deltaZ <= 1
    }

    @JvmStatic
    fun isAdjacentToAny(pos: BlockPos, positions: MutableList<BlockPos>): Boolean {
        for (adjacent in positions) {
            if (isAdjacent(pos, adjacent)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun getRandomXWiseDir(dir: Direction, random: RandomSource): Direction {
        return if (random.nextBoolean()) {
            dir.clockWise
        } else {
            dir.counterClockWise
        }
    }
}
