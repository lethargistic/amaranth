package dev.maksiks.amaranth.worldgen.structure;

import com.mojang.serialization.MapCodec;
import dev.maksiks.amaranth.worldgen.structure.piece.DruidicWitchLabPiece;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class DruidicWitchLabStructure extends Structure {
    public static final MapCodec<DruidicWitchLabStructure> CODEC = simpleCodec(DruidicWitchLabStructure::new);

    public DruidicWitchLabStructure(StructureSettings settings) { super(settings); }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext ctx) {
        ChunkPos cp = ctx.chunkPos();
        BlockPos pos = new BlockPos(cp.getMiddleBlockX(), 62, cp.getMiddleBlockZ());

        return Optional.of(new GenerationStub(pos, builder ->
                builder.addPiece(new DruidicWitchLabPiece(ctx.structureTemplateManager(), ctx.random(), pos))));
    }

    @Override
    public StructureType<?> type() { return ModStructures.DRUIDIC_WITCH_LAB.get(); }
}
