package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.worldgen.biome.ModBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;

import static dev.maksiks.amaranth.ClientConfig.HIDE_DESOLATE_ICE_FIELDS_FOG;
import static dev.maksiks.amaranth.event.ModEventUtils.isPlayerUnderground;

public class CustomFogHandlerSharedEvent {
    private static float progress = 0f;

    private static final float TRANSITION_SPEED_IN = 0.005f;
    private static final float TRANSITION_SPEED_OUT = 0.0005f;
    private static final float TRANSITION_THRESHOLD = 0.001f;

    private static void stepProgress() {
        float target = isInDesolateIceFieldsAndValid() ? 1f : 0f;
        float speed = isInDesolateIceFieldsAndValid() ? TRANSITION_SPEED_IN : TRANSITION_SPEED_OUT;
        progress = Mth.lerp(speed, progress, target);
    }

    private static boolean isInDesolateIceFieldsAndValid() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return false;

        // checking if the player is underground/not skylit
        if (isPlayerUnderground(mc.player, mc.level)) return false;

        ResourceKey<Biome> biome = mc.level.getBiome(mc.player.blockPosition()).unwrapKey().orElse(null);
        return biome != null && biome.equals(ModBiomes.DESOLATE_ICE_FIELDS);
    }

    public static float[] computeFogColor(float vanillaR, float vanillaG, float vanillaB) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return null;

        stepProgress();
        if (progress < TRANSITION_THRESHOLD) return null;

        float r = Mth.lerp(progress, vanillaR, 0.03f);
        float g = Mth.lerp(progress, vanillaG, 0.04f);
        float b = Mth.lerp(progress, vanillaB, 0.05f);
        return new float[]{r, g, b};
    }

    public record FogResult(float near, float far, boolean cancel) {}

    public static FogResult renderFog(float vanillaNear, float vanillaFar) {
        if (HIDE_DESOLATE_ICE_FIELDS_FOG.getAsBoolean()) return null;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return null;

        if (progress < TRANSITION_THRESHOLD) return null;

        float near = Mth.lerp(progress, vanillaNear, -1.0f);
        float far = Mth.lerp(progress, vanillaFar, 7.0f);
        return new FogResult(near, far, true);
    }

}
