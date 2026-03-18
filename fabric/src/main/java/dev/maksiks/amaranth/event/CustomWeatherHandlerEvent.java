package dev.maksiks.amaranth.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

// TODO fabrec: test havey
public class CustomWeatherHandlerEvent {
    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register((Minecraft client)
                -> CustomWeatherHandlerSharedEvent.clientTick());
    }
}
