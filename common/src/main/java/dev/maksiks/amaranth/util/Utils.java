package dev.maksiks.amaranth.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    TriState canSustainPlant(BlockState soil, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return soil.getBlock().canSustainPlant(level, soilPosition, facing, plant);
    }
}
