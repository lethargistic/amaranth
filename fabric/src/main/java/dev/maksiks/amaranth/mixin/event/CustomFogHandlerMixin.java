package dev.maksiks.amaranth.mixin.event;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.maksiks.amaranth.event.CustomFogHandlerSharedEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// TODO fabrec: test events heavy!!!
// recreating Neo's hook
@Mixin(FogRenderer.class)
public class CustomFogHandlerMixin {
    @Inject(at = @At("TAIL"), method = "setupFog")
    private static void onRenderFog(Camera Camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean shouldCreateFog, float partialTick, CallbackInfo ci) {
        CustomFogHandlerSharedEvent.FogResult result
                = CustomFogHandlerSharedEvent.renderFog(
                RenderSystem.getShaderFogStart(),
                RenderSystem.getShaderFogEnd());
        if (result == null) return;

        RenderSystem.setShaderFogStart(result.near());
        RenderSystem.setShaderFogEnd(result.far());
    }

    @Shadow private static float fogRed;
    @Shadow
    private static float fogGreen;
    @Shadow private static float fogBlue;

    @Inject(
            method = "setupColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V",
                    ordinal = 0,
                    remap = false
            )
    )
    private static void onSetupColor(Camera activeRenderInfo, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier, CallbackInfo ci) {
        float[] result = CustomFogHandlerSharedEvent.computeFogColor(
                fogRed,
                fogGreen,
                fogBlue
        );
        if (result == null) return;

        fogRed = result[0];
        fogGreen = result[1];
        fogBlue = result[2];
    }
}
