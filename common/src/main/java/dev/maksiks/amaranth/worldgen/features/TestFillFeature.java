package dev.maksiks.amaranth.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TestFillFeature extends Feature<NoneFeatureConfiguration> {
    public TestFillFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos pos = ctx.origin();

        level.setBlock(pos, Blocks.RED_GLAZED_TERRACOTTA.defaultBlockState(), 2);
        level.setBlock(pos.above(), Blocks.TRIPWIRE.defaultBlockState(), 2);

        return false;
    }
}
