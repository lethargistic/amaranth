package dev.maksiks.amaranth.worldgen.tree.foliage_placer.custom_placer;

import com.mojang.datafixers.Products.P3
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu
import dev.maksiks.amaranth.worldgen.tree.TreeUtils
import dev.maksiks.amaranth.worldgen.tree.foliage_placer.ModFoliagePlacerTypes
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.custom_placer.FrontierBlossomTrunkPlacer
import dev.maksiks.twigonometry.api.*
import dev.maksiks.twigonometry.api.LeafPlacerContext.HorizontalLayer
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType

class FrontierBlossomFoliagePlacer(
    radius: IntProvider,
    offset: IntProvider,
    val height: Int
) : WildcardFoliagePlacer(radius, offset) {
    companion object {
        @JvmStatic
        var CODEC: MapCodec<FrontierBlossomFoliagePlacer> =
            RecordCodecBuilder.mapCodec { instance ->
                blobParts(instance).apply(instance, ::FrontierBlossomFoliagePlacer)
            }

        @JvmStatic
        private fun blobParts(instance: Instance<FrontierBlossomFoliagePlacer>): P3<
                Mu<FrontierBlossomFoliagePlacer>,
                IntProvider,
                IntProvider,
                Int
                > {
            return foliagePlacerParts(instance).and(
                Codec.intRange(0, 16).fieldOf("height").forGetter { it.height }
            )
        }
    }

    protected override fun type(): FoliagePlacerType<*> = ModFoliagePlacerTypes.FRONTIER_BLOSSOM_FOLIAGE_PLACER.get()

    override fun createWildcardFoliage(
        level: LevelSimulatedReader,
        blockSetter: FoliageSetter,
        random: RandomSource,
        config: TreeConfiguration,
        maxFreeTreeHeight: Int,
        attachment: WildcardFoliageAttachment,
        foliageHeight: Int,
        foliageRadius: Int
    ) {
        val trunkPos = attachment.pos().below();
        val ctx = LeafPlacerContext.ctx(level, blockSetter, random, config, debug = false);

        fun at(height: Int): BlockPos = trunkPos.above(height)
        var curY = 0;
        fun bump() {
            curY += 1
        }

        fun lower() {
            curY -= 1
        }
        bump()

        attachment.require("variant", "dir")
        val variant = attachment.getRequired<FrontierBlossomTrunkPlacer.Variant?>("variant")
        val dir = attachment.getRequired<Direction>("dir")

        if (variant == FrontierBlossomTrunkPlacer.Variant.BUNS) {
            ctx.diamond(at(curY), 1)
            bump()
            ctx.placeLeaf(at(curY))
            if (random.nextInt(100) < 10) {
                ctx.placeLeaf(
                    at(curY)
                        .relative(Direction.Plane.HORIZONTAL.getRandomDirection(random), 1)
                )
            }
        }
        if (variant == FrontierBlossomTrunkPlacer.Variant.DROOPY) {
            val diagonalDroop = ICustomLeafPlacer { pos, x, z, dist ->
                ctx.placeLeaf(pos)
                if (random.nextBoolean()) {
                    ctx.placeLeaf(pos.below())
                }
            }

            ctx.diamond(at(curY), 1)
            ctx.incSquare(
                at(curY), 0,
                HorizontalLayer(25, cap = 25, pattern = LayerPattern.NOT_PLUS, custom = diagonalDroop)
            )

            // droop itself
            val chiefDir =
                generateSequence { Direction.Plane.HORIZONTAL.getRandomDirection(random) }.first { it != dir }
            val edgeDir = generateSequence { TreeUtils.getRandomXWiseDir(chiefDir, random) }.first { it != dir }
            val mirrorEdge = random.nextInt(4) == 0

            ctx.placeLeaf(at(curY - 1).relative(edgeDir))
            if (mirrorEdge) ctx.placeLeaf(at(curY - 1).relative(edgeDir.opposite))
            repeat(2) { ctx.placeLeaf(at(curY - it - 1).relative(chiefDir)) }

            bump()
            ctx.placeLeaf(at(curY))
            if (random.nextInt(100) < 20) {
                ctx.placeLeaf(
                    at(curY)
                        .relative(Direction.Plane.HORIZONTAL.getRandomDirection(random), 1)
                )
            }
        }
    }

    override fun foliageHeight(random: RandomSource, height: Int, config: TreeConfiguration): Int =
        this.height

    override fun shouldSkipLocation(
        random: RandomSource,
        localX: Int,
        localY: Int,
        localZ: Int,
        range: Int,
        large: Boolean
    ): Boolean {
        return localX == range && localZ == range && (random.nextInt(2) == 0 || localY == 0)
    }
}
