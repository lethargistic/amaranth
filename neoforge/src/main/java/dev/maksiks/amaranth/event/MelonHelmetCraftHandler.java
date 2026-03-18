package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class MelonHelmetCraftHandler {
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent e) {
        MelonHelmetCraftHandlerSharedEvent.itemCrafted(e.getEntity(), e.getCrafting());
    }
}
