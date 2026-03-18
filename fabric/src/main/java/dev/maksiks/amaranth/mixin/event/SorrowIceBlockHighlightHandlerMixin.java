package dev.maksiks.amaranth.mixin.event;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.event.SorrowIceBlockHighlightHandlerSharedEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// a bit different from Neo because my cancel was cancelling the whole thing
// so instead i mixin'd into the method for it directly and cancel that
// I assume Neo just has a thing for that or something but i really should learn how
// is it that Neo's cancel only cancels the correct if block/method (?) and whatnot?
@Mixin(LevelRenderer.class)
public class SorrowIceBlockHighlightHandlerMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @Unique
    private boolean currentResult = false;
    @Unique
    private MultiBufferSource currentBuffer;

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"
            )
    )
    private void onDrawBlockHighlight(
            DeltaTracker deltaTracker,
            boolean renderBlockOutline,
            Camera camera,
            GameRenderer gameRenderer,
            LightTexture lightTexture,
            Matrix4f frustumMatrix,
            Matrix4f projectionMatrix,
            CallbackInfo ci,
            @Local MultiBufferSource.BufferSource buffer

    ) {
        BlockHitResult hit = (BlockHitResult) this.minecraft.hitResult;

        currentBuffer = buffer;

        currentResult = SorrowIceBlockHighlightHandlerSharedEvent
                .checkBlockHighlight(hit, camera);
    }

    @Inject(
            method = "renderHitOutline",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRenderHitOutline(PoseStack poseStack, VertexConsumer consumer, Entity entity, double camX, double camY, double camZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (currentResult) {
            Constants.LOG.info("Amaranth: hi im looking at bloc");
            ci.cancel();

            SorrowIceBlockHighlightHandlerSharedEvent
                    .drawCustomOutline(poseStack, camX, camY, camZ, currentBuffer, pos);
        }
    }
}
