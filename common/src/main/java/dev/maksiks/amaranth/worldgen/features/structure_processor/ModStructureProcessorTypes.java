package dev.maksiks.amaranth.worldgen.features.structure_processor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.HashMap;

public class ModStructureProcessorTypes {
    public static final HashMap<String, Supplier<? extends StructureProcessorType<?>>> STRUCTURE_PROCESSOR_TYPE_MAP = new HashMap<>();

    public static final Supplier<StructureProcessorType<GiganticSatistreeStructureProcessor>>
            GIGANTIC_SATISTREE_PROCESSOR =
            register(
                    "gigantic_satistree_processor",
                    () -> () -> GiganticSatistreeStructureProcessor.CODEC
            );

    private static <T extends StructureProcessor> Supplier<StructureProcessorType<T>> register(String key, Supplier<StructureProcessorType<T>> processor) {
        STRUCTURE_PROCESSOR_TYPE_MAP.put(key, processor);
        return Suppliers.memoize(processor);
    }
}