package dev.maksiks.amaranth.worldgen.structure.piece;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.HashMap;

public class ModPieces {
    public static final HashMap<String, Supplier<? extends StructurePieceType>> PIECE_MAP = new HashMap<>();

    public static final Supplier<StructurePieceType> DRUIDIC_WITCH_LAB_PIECE =
            register("druidic_witch_lab_structure_piece", () -> DruidicWitchLabPiece::new);

    public static <P extends StructurePieceType> Supplier<P> register(String key, Supplier<P> piece) {
        Supplier<P> memoized = Suppliers.memoize(piece);
        PIECE_MAP.put(key, memoized);
        return memoized;
    }
}