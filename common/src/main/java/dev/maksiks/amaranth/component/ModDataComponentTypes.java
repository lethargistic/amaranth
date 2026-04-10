package dev.maksiks.amaranth.component;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class ModDataComponentTypes {
    public static final HashMap<String, Supplier<DataComponentType<?>>> DATA_COMPONENT_TYPE_MAP = new HashMap<>();

    public static final Supplier<DataComponentType<Boolean>> STACKABLE_TIERED = registerDataComponent("stackable_tiered", Codec.BOOL);

    private static <T>Supplier<DataComponentType<T>> registerDataComponent(String name, Codec<T> codec) {
        Supplier<DataComponentType<T>> component = () -> DataComponentType.<T>builder().persistent(codec).build();
        DATA_COMPONENT_TYPE_MAP.put(name, (Supplier<DataComponentType<?>>) (Supplier<?>) component);
        return Suppliers.memoize(component);
    }
}
