package dev.maksiks.amaranth.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class CressetFlowerBlock extends Block {
    public CressetFlowerBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Block.box(7, 3, 7, 9, 16, 9);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    // leaves for building
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (level.getBlockState(pos.above()).is(BlockTags.LEAVES)
                || Block.canSupportCenter(level, pos.above(), Direction.DOWN)) && !level.isWaterAt(pos);
    }

    @Override
    protected BlockState updateShape(
            BlockState p_154713_, Direction p_154714_, BlockState p_154715_, LevelAccessor p_154716_, BlockPos p_154717_, BlockPos p_154718_
    ) {
        return p_154714_ == Direction.UP && !this.canSurvive(p_154713_, p_154716_, p_154717_)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(p_154713_, p_154714_, p_154715_, p_154716_, p_154717_, p_154718_);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.amaranth.cresset_flower.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
