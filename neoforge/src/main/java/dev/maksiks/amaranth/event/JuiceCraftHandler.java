package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class JuiceCraftHandler {
    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent e) {
        JuiceCraftHandlerSharedEvent.itemCrafted(e.getEntity(), e.getCrafting());
    }
}
