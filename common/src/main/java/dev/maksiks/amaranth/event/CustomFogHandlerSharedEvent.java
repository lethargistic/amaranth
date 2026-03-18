package dev.maksiks.amaranth.event;

import com.mojang.blaze3d.shaders.FogShape;
import dev.maksiks.amaranth.worldgen.biome.ModBiomes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;

import static dev.maksiks.amaranth.ClientConfig.HIDE_DESOLATE_ICE_FIELDS_FOG;
import static dev.maksiks.amaranth.event.ModEventUtils.isPlayerUnderground;

public class CustomFogHandlerSharedEvent {
    private static float currentRed = 1f, currentGreen = 1f, currentBlue = 1f;
    private static float currentNear = 0f, currentFar = 192f;

    private static float targetRed = 1f, targetGreen = 1f, targetBlue = 1f;
    private static float targetNear = 0f, targetFar = 192f;

    private static final float TRANSITION_SPEED_IN = 0.005f;
    private static final float TRANSITION_SPEED_OUT = 0.0005f;
    private static final float TRANSITION_THRESHOLD = 0.001f;

    private static void stepTowardTarget() {
        float speed = isInDesolateIceFieldsAndValid() ? TRANSITION_SPEED_IN : TRANSITION_SPEED_OUT;

        currentRed = Mth.lerp(speed, currentRed, targetRed);
        currentGreen = Mth.lerp(speed, currentGreen, targetGreen);
        currentBlue = Mth.lerp(speed, currentBlue, targetBlue);
        currentNear = Mth.lerp(speed, currentNear, targetNear);
        currentFar = Mth.lerp(speed, currentFar, targetFar);
    }

    private static boolean isInDesolateIceFieldsAndValid() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return false;

        // checking if the player is underground/not skylit
        if (isPlayerUnderground(mc.player, mc.level)) return false;

        ResourceKey<Biome> biome = mc.level.getBiome(mc.player.blockPosition()).unwrapKey().orElse(null);
        return biome != null && biome.equals(ModBiomes.DESOLATE_ICE_FIELDS);
    }

    private static boolean isTransitioning() {
        return Math.abs(currentRed - targetRed) > TRANSITION_THRESHOLD ||
                Math.abs(currentGreen - targetGreen) > TRANSITION_THRESHOLD ||
                Math.abs(currentBlue - targetBlue) > TRANSITION_THRESHOLD ||
                Math.abs(currentNear - targetNear) > TRANSITION_THRESHOLD ||
                Math.abs(currentFar - targetFar) > TRANSITION_THRESHOLD;
    }

    public static float[] computeFogColor(float vanillaR, float vanillaG, float vanillaB) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return null;

        if (isInDesolateIceFieldsAndValid()) {
            targetRed = 0.03f;
            targetGreen = 0.04f;
            targetBlue = 0.05f;
        } else {
            targetRed = vanillaR;
            targetGreen = vanillaG;
            targetBlue = vanillaB;
        }

        stepTowardTarget();

        return new float[]{currentRed, currentGreen, currentBlue};
    }

    public record FogResult(float near, float far, boolean cancel) {}

    public static FogResult renderFog(float vanillaNear, float vanillaFar) {
        if (HIDE_DESOLATE_ICE_FIELDS_FOG.getAsBoolean()) return null;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return null;

        if (isInDesolateIceFieldsAndValid()) {
            targetNear = -1.0f;
            targetFar = 7.0f;
        } else {
            targetNear = vanillaNear;
            targetFar = vanillaFar;
        }

        stepTowardTarget();

        boolean shouldCancel = isInDesolateIceFieldsAndValid() || isTransitioning();
        return new FogResult(currentNear, currentFar, shouldCancel);
    }
}
