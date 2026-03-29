package dev.maksiks.amaranth.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoseryMegaBoulder extends Feature<NoneFeatureConfiguration> {
    public RoseryMegaBoulder(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static final double NOISE_STRENGTH = 4.5;
    private static final double NOISE_FREQUENCY = 1.0;
    private static final double STAIR_EDGE_NOISE = 0.88;

    private static final double STAIR_MOSS_FREQUENCY = 0.88;
    private static final double STAIR_MOSS_THRESHOLD = 0.5;

    private static final double ANDESITE_BOTTOM_THRESHOLD = 0.3;

    private static final double COBBLE_FREQUENCY = 0.3;
    private static final double COBBLE_THRESHOLD = 0.44;

    private static final double MOSSY_COBBLE_THRESHOLD = 0.4;
    private static final double MOSSY_STAIR_THRESHOLD = 0.6;

    private static final double ANDESITE_IN_STONE_THRESHOLD = 0.2;
    private static final double ANDESITE_POLISHED_THRESHOLD = 0.4;
    private static final double ANDESITE_FREQUENCY = 0.25;

    private static final double MOSS_CARPET_THRESHOLD = 0.65;
    private static final double GRASS_THRESHOLD = 0.35;
    private static final double LEDGE_CARPET_BONUS = 0.3;

    private static final boolean RANDOM_YAW = true;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        int extraBlocks = random.nextInt(3);

        // TIL superellipsoids
        int hLength = 13 + random.nextInt(5);
        int hWidth = 5 + extraBlocks;
        int hHeight = 5 + extraBlocks;
        double p = 1.8 + random.nextDouble() * (0.7 - 0.0); // 2.0 = is an ellipse, 4-6 = flat sides + rounded corners

        double yawAngle = RANDOM_YAW ? random.nextDouble() * Math.PI * 2.0 : 0.0;
        double cosY = Math.cos(yawAngle);
        double sinY = Math.sin(yawAngle);

        double tiltAngle = random.nextDouble() * (0.8 - 0.2) * Math.PI / 2.0;
        double cosT = Math.cos(tiltAngle);
        double sinT = Math.sin(tiltAngle) * (random.nextBoolean() ? 1.0 : -1.0);

        int ledgeHeight = 3 + random.nextInt(4);
        boolean ledgeOnEast = random.nextBoolean();

        ImprovedNoise noise = new ImprovedNoise(new XoroshiroRandomSource(random.nextLong()));

        List<BlockPos> positions = new ArrayList<>();

        double ledgeCornerRounding = 0.99 - random.nextDouble() * (0.3 - 0.0);

        double ledgeP = p + (8.0 - p) * (1.0 - ledgeCornerRounding);

        // structure
        int bound = hLength + hHeight + ledgeHeight;
        for (int x = -bound; x <= bound; x++) {
            for (int z = -bound; z <= bound; z++) {
                for (int y = -bound; y <= bound; y++) {
                    double yx = x * cosY - z * sinY;
                    double yz = x * sinY + z * cosY;

                    double rx = yx * cosT - y * sinT;
                    double ry = yx * sinT + y * cosT;
                    double rz = yz;

                    boolean onLedgeSide = ledgeOnEast ? (rx > 0) : (rx < 0);

                    double ledgeFactor = onLedgeSide
                            ? Math.abs(rx / hLength)
                            : 0.0;

                    double effectiveHHeight = hHeight + ledgeHeight * ledgeFactor;
                    double yExp = onLedgeSide ? ledgeP : p;

                    double val = Math.pow(Math.abs(rx / hLength), p)
                            + Math.pow(Math.abs(rz / hWidth), p)
                            + Math.pow(Math.abs(ry / effectiveHHeight), yExp);
                    if (val < 1.0) {
                        positions.add(origin.offset(x, y, z));
                    }
                }
            }
        }

        Set<BlockPos> posSet = new HashSet<>(positions);
        int bottomY = origin.getY() - hHeight;
        int totalHeight = hHeight * 2;

        // painting
        for (BlockPos pos : posSet) {
            boolean aboveEmpty = !posSet.contains(pos.above());
            boolean northEmpty = !posSet.contains(pos.north());
            boolean southEmpty = !posSet.contains(pos.south());
            boolean eastEmpty = !posSet.contains(pos.east());
            boolean westEmpty = !posSet.contains(pos.west());

            double n = noise.noise(
                    pos.getX() * NOISE_FREQUENCY,
                    pos.getY() * NOISE_FREQUENCY,
                    pos.getZ() * NOISE_FREQUENCY
            );

            boolean isBottomSurface = !posSet.contains(pos.below());
            boolean isSubBottom = !posSet.contains(pos.below().below());

            boolean isTopSurface = aboveEmpty;
            boolean isSubSurface = !aboveEmpty && !posSet.contains(pos.above().above());
            boolean isSideFace = (northEmpty || southEmpty) && !eastEmpty && !westEmpty;

            double middleCutoff = hLength * (0.6 + n * STAIR_EDGE_NOISE);
            boolean isMiddleSection = Math.abs(pos.getX() - origin.getX()) < middleCutoff;

            BlockState state;

            if ((isBottomSurface || isSubBottom) && pos.getY() <= bottomY
                    + (totalHeight * ANDESITE_BOTTOM_THRESHOLD) + (n * NOISE_STRENGTH)) {
                double polishedN = noise.noise(
                        pos.getX() * ANDESITE_FREQUENCY + 19,
                        pos.getY() * ANDESITE_FREQUENCY + 19,
                        pos.getZ() * ANDESITE_FREQUENCY + 19
                );
                state = (polishedN + 1.0) / 2.0 < ANDESITE_POLISHED_THRESHOLD
                        ? Blocks.ANDESITE.defaultBlockState()
                        : Blocks.POLISHED_ANDESITE.defaultBlockState();
            } else if (isTopSurface && isSideFace && isMiddleSection) {
                double stairN = noise.noise(
                        pos.getX() * STAIR_MOSS_FREQUENCY + 31,
                        pos.getY() * STAIR_MOSS_FREQUENCY + 31,
                        pos.getZ() * STAIR_MOSS_FREQUENCY + 31
                );
                boolean placeMoss = (stairN + 1.0) / 2.0 < STAIR_MOSS_THRESHOLD;

                if (placeMoss) {
                    state = Blocks.MOSS_BLOCK.defaultBlockState();
                } else {
                    Direction facing = northEmpty ? Direction.SOUTH : Direction.NORTH;
                    double mossyN = noise.noise(
                            pos.getX() * STAIR_MOSS_FREQUENCY + 62,
                            pos.getY() * STAIR_MOSS_FREQUENCY + 62,
                            pos.getZ() * STAIR_MOSS_FREQUENCY + 62
                    );
                    boolean mossy = (mossyN + 1.0) / 2.0 < MOSSY_STAIR_THRESHOLD;
                    state = (mossy ? Blocks.MOSSY_COBBLESTONE_STAIRS : Blocks.COBBLESTONE_STAIRS)
                            .defaultBlockState()
                            .setValue(StairBlock.FACING, facing)
                            .setValue(StairBlock.HALF, Half.BOTTOM);
                }
            } else if (isTopSurface || isSubSurface) {
                state = Blocks.MOSS_BLOCK.defaultBlockState();
            } else {
                double cobbleN = noise.noise(
                        pos.getX() * COBBLE_FREQUENCY + 57,
                        pos.getY() * COBBLE_FREQUENCY + 57,
                        pos.getZ() * COBBLE_FREQUENCY + 57
                );
                if ((cobbleN + 1.0) / 2.0 < COBBLE_THRESHOLD) {
                    double mossyN = noise.noise(
                            pos.getX() * COBBLE_FREQUENCY + 83,
                            pos.getY() * COBBLE_FREQUENCY + 83,
                            pos.getZ() * COBBLE_FREQUENCY + 83
                    );
                    state = (mossyN + 1.0) / 2.0 < MOSSY_COBBLE_THRESHOLD
                            ? Blocks.MOSSY_COBBLESTONE.defaultBlockState()
                            : Blocks.COBBLESTONE.defaultBlockState();
                } else {
                    double andesiteN = noise.noise(
                            pos.getX() * ANDESITE_FREQUENCY + 41,
                            pos.getY() * ANDESITE_FREQUENCY + 41,
                            pos.getZ() * ANDESITE_FREQUENCY + 41
                    );
                    state = (andesiteN + 1.0) / 2.0 < ANDESITE_IN_STONE_THRESHOLD
                            ? Blocks.ANDESITE.defaultBlockState()
                            : Blocks.STONE.defaultBlockState();
                }
            }

            level.setBlock(pos, state, 2);
        }

        // decor
        int ledgeTopY = origin.getY() + hHeight + ledgeHeight;
        for (BlockPos pos : posSet) {
            if (!posSet.contains(pos.above())) {
                BlockState below = level.getBlockState(pos);
                if (below.is(Blocks.MOSS_BLOCK)) {
                    BlockPos above = pos.above();
                    if (level.getBlockState(above).isAir()) {
                        double carpetN = noise.noise(
                                pos.getX() * STAIR_MOSS_FREQUENCY + 91,
                                pos.getY() * STAIR_MOSS_FREQUENCY + 91,
                                pos.getZ() * STAIR_MOSS_FREQUENCY + 91
                        );
                        double carpetNorm = (carpetN + 1.0) / 2.0;

                        // bonus chance the closer we to the ledge top
                        double heightFraction = Math.max(0, (double)(pos.getY() - origin.getY()) / (ledgeTopY - origin.getY()));
                        double threshold = MOSS_CARPET_THRESHOLD + heightFraction * LEDGE_CARPET_BONUS;

                        if (carpetNorm < threshold) {
                            double grassN = noise.noise(
                                    pos.getX() * STAIR_MOSS_FREQUENCY + 114,
                                    pos.getY() * STAIR_MOSS_FREQUENCY + 114,
                                    pos.getZ() * STAIR_MOSS_FREQUENCY + 114
                            );
                            boolean placeGrass = (grassN + 1.0) / 2.0 < GRASS_THRESHOLD;
                            level.setBlock(above, placeGrass
                                    ? Blocks.SHORT_GRASS.defaultBlockState()
                                    : Blocks.MOSS_CARPET.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        return true;
    }
}