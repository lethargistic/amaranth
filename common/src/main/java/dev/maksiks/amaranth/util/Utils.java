package dev.maksiks.amaranth.util;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Utils {
    /// note: call only after registries exist
    public static String findBlockId(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }
    public static String findBlockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public static String findBlockIdByMap(Supplier<? extends Block> block) {
        List<String> keys = Utils.getMapKeys(ModBlocks.BLOCK_MAP, block).toList();

        if (keys.isEmpty()) throw new IllegalStateException("Amaranth: block not in BLOCK_MAP: " + block);
        if (keys.size() > 1) throw new IllegalStateException("Amaranth: couldn't resolve flower pot id (there's multiple): " + block);
        return keys.getFirst();

    }

    public static String findItemId(Supplier<? extends Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).getPath();
    }
    public static String findItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public static <K, V> Stream<K> getMapKeys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}
