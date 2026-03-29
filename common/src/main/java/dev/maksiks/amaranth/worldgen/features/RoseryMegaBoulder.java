package dev.maksiks.amaranth.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

import static dev.maksiks.amaranth.worldgen.features.ModFeatureUtils.hasNonSolidBelow;

public class RoseryMegaBoulder extends Feature<NoneFeatureConfiguration> {
    public RoseryMegaBoulder(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        // TIL superellipsoids
        int hLength = 20 + random.nextInt(1);
        int hWidth = 10 + random.nextInt(1);
        int hHeight = 8 + random.nextInt(1);
        double p = 4.0; // 2.0 = should be an ellipse, 4-6 = flat sides + rounded corners

        List<BlockPos> positions = new ArrayList<>();


        for (int x = -hLength; x <= hLength; x++) {
            for (int z = -hWidth; z <= hWidth; z++) {
                for (int y = -hHeight; y <= hHeight; y++) {
                    double val = Math.pow(Math.abs((double) x / hLength), p)
                            + Math.pow(Math.abs((double) z / hWidth), p)
                            + Math.pow(Math.abs((double) y / hHeight), p);
                    if (val <= 1.0) {
                        positions.add(origin.offset(x, y, z));
                    }
                }
            }
        }

        BlockPos curBase = origin;
        int limit = 4;
//        while (hasNonSolidBelow(level, positions)) {
//            curBase = curBase.below();
//            List<BlockPos> shifted = new ArrayList<>(positions.size());
//            for (BlockPos pos : positions) {
//                shifted.add(pos.below());
//            }
//            positions = shifted;
//
//            limit--;
//            if (limit < 0) return true;
//            if (curBase.getY() < level.getMinBuildHeight() + 2) return true;
//        }

        for (BlockPos pos : positions) {
            level.setBlock(pos, Blocks.STONE.defaultBlockState(), 2);
        }

        return true;
    }
}