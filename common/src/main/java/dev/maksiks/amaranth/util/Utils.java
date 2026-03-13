package dev.maksiks.amaranth.util;

import com.google.common.base.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Utils {
    public static String findBlockId(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }
    public static String findItemId(Supplier<? extends Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).getPath();
    }
}
