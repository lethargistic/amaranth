package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class CustomFogHandler {
    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor e) {
        float[] result = CustomFogHandlerSharedEvent.computeFogColor(e.getRed(), e.getGreen(), e.getBlue());

        if (result == null) {
            return;
        }

        e.setRed(result[0]);
        e.setGreen(result[1]);
        e.setBlue(result[2]);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onFogRender(ViewportEvent.RenderFog e) {
        if (e.isCanceled()) return;

        CustomFogHandlerSharedEvent.FogResult result
                = CustomFogHandlerSharedEvent.renderFog(e.getNearPlaneDistance(), e.getFarPlaneDistance());
        if (result == null) return;

        e.setNearPlaneDistance(result.near());
        e.setFarPlaneDistance(result.far());
        if (result.cancel()) e.setCanceled(true);
    }
}