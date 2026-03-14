package dev.maksiks.amaranth.util;

import com.google.common.base.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class Utils {
    // note: call only after registries exist
    public static String findBlockId(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }
    public static String findItemId(Supplier<? extends Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).getPath();
    }

    public static <K, V> Stream<K> getMapKeys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}
