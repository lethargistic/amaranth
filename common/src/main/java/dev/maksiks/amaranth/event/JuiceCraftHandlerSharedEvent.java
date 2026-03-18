package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class JuiceCraftHandlerSharedEvent {
    public static void itemCrafted(Player entity, ItemStack crafting) {
        if (crafting.is(ModBlocks.WISTERIA_LOG.get().asItem())) {
            ItemStack secondary = new ItemStack(ModItems.WISTERIA_JUICE.get().asItem());
            if (!entity.getInventory().add(secondary)) {
                entity.drop(secondary, false);
            }
        }
    }
}
