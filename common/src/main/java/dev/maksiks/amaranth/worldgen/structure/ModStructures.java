package dev.maksiks.amaranth.worldgen.structure;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.HashMap;

public class ModStructures {
    public static final HashMap<String, Supplier<? extends StructureType<?>>> STRUCTURE_TYPE_MAP = new HashMap<>();

    public static final Supplier<StructureType<DruidicWitchLabStructure>> DRUIDIC_WITCH_LAB =
            register("druidic_witch_lab", () -> () -> DruidicWitchLabStructure.CODEC);

    public static <S extends StructureType<?>> Supplier<S> register(String key, Supplier<S> type) {
        Supplier<S> memoized = Suppliers.memoize(type);
        STRUCTURE_TYPE_MAP.put(key, memoized);
        return memoized;
    }
}