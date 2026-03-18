package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class CustomWeatherHandler {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre e) {
        CustomWeatherHandlerSharedEvent.clientTick();
    }
}