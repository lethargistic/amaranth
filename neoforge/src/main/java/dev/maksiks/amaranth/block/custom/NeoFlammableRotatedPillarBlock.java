package dev.maksiks.amaranth.block.custom;

import com.google.common.base.Supplier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.Map;

import static dev.maksiks.amaranth.block.ModBlocks.MOD_STRIPPABLES;

public class NeoFlammableRotatedPillarBlock extends FlammableRotatedPillarBlock {
    public NeoFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            for (Map.Entry<Supplier<? extends Block>, Supplier<Block>> entry : MOD_STRIPPABLES.entrySet()) {
                if (state.is(entry.getKey().get())) {
                    return entry.getValue().get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                }
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
