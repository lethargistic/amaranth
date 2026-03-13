package dev.maksiks.amaranth.block.custom;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.util.ItemAbility;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static dev.maksiks.amaranth.block.ModBlocks.MOD_STRIPPABLES;

public class FlammableRotatedPillarBlock extends RotatedPillarBlock {
    public FlammableRotatedPillarBlock(Properties properties) {
        super(properties);
        ModBlocks.registerFabricFlammability(() -> this, 5, 5);
    }

    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    // TODO: fix tool modified state / see if it just works anyway as in my map?
//    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
//        if (context.getItemInHand().getItem() instanceof AxeItem) {
//            for (Map.Entry<Supplier<Block>, Supplier<Block>> entry : MOD_STRIPPABLES.entrySet()) {
//                if(state.is(entry.getKey().get())) {
//                    return entry.getValue().get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//                }
//            }
//        }
//
//        return super.getToolModifiedState(state, context, itemAbility, simulate);
//    }
}
