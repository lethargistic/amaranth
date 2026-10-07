package dev.maksiks.amaranth.worldgen.structure.piece;

import dev.maksiks.amaranth.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;

public class DruidicWitchLabPiece extends TemplateStructurePiece {
    private static final ResourceLocation TEMPLATE =
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "druidic_witch_lab");

    public DruidicWitchLabPiece(StructureTemplateManager mgr, RandomSource random, BlockPos pos) {
        super(ModPieces.DRUIDIC_WITCH_LAB_PIECE.get(), 0, mgr, TEMPLATE, TEMPLATE.toString(),
                makeSettings(mgr, TEMPLATE, Rotation.getRandom(random)), pos);
    }

    public DruidicWitchLabPiece(StructurePieceSerializationContext ctx, CompoundTag tag) {
        super(ModPieces.DRUIDIC_WITCH_LAB_PIECE.get(), tag, ctx.structureTemplateManager(),
                id -> makeSettings(ctx.structureTemplateManager(), id,
                        Rotation.valueOf(tag.getString("Rot"))));
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager mgr, ResourceLocation id, Rotation rot) {
        Vec3i size = mgr.getOrCreate(id).getSize();
        return new StructurePlaceSettings()
                .setRotation(rot)
                .setRotationPivot(new BlockPos(size.getX() / 2, 0, size.getZ() / 2))
                .addProcessor(new BlockIgnoreProcessor(List.of(Blocks.STRUCTURE_BLOCK, Blocks.JIGSAW)));
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tag) {
        super.addAdditionalSaveData(ctx, tag);
        tag.putString("Rot", this.placeSettings.getRotation().name());
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level,
                                    RandomSource random, BoundingBox box) {
    }

    private void placeIfReplaceable(WorldGenLevel level, BlockState state, BlockPos world, BoundingBox box) {
        if (box.isInside(world) && isReplaceableByStructures(level.getBlockState(world))) {
            level.setBlock(world, state, 2);
        }
    }

    private void fillColumnDownWorld(WorldGenLevel level, BlockState state, BlockPos start, BoundingBox box) {
        BlockPos.MutableBlockPos p = start.mutable();
        if (!box.isInside(p)) return;
        while (isReplaceableByStructures(level.getBlockState(p))
                && p.getY() > level.getMinBuildHeight() + 1) {
            level.setBlock(p, state, 2);
            p.move(Direction.DOWN);
        }
    }

    private static final BlockPos[] WATER_ROOTS = {
            new BlockPos(4, -1, 3),
            new BlockPos(3, -1, 5)
    };

    private static final BlockPos[] PILLARS = {
            new BlockPos(1, 0, 3),
            new BlockPos(5, 0, 3),
            new BlockPos(2, 0, 7),
            new BlockPos(7, 0, 7)
    };

    @Override
    public void postProcess(WorldGenLevel level, StructureManager sm, ChunkGenerator gen,
                            RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        super.postProcess(level, sm, gen, random, box, chunkPos, pos);

        BlockState log = Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState();
        for (BlockPos local : PILLARS) {
            BlockPos world = StructureTemplate
                    .calculateRelativePosition(this.placeSettings, local.below())
                    .offset(this.templatePosition);
            fillColumnDownWorld(level, log, world, box);
        }

        BlockState cherry = Blocks.CHERRY_WOOD.defaultBlockState();
        for (BlockPos local : WATER_ROOTS) {
            BlockPos world = StructureTemplate
                    .calculateRelativePosition(this.placeSettings, local)
                    .offset(this.templatePosition);
            placeIfReplaceable(level, cherry, world, box);
        }
    }
}
