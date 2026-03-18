package dev.maksiks.amaranth.event;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.maksiks.amaranth.Constants;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class SorrowIceBlockHighlightHandler {
    @SubscribeEvent
    public static void onDrawBlockHighlight(RenderHighlightEvent.Block event) {
        BlockHitResult hit = event.getTarget();
        Camera camera = event.getCamera();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();

        boolean result = SorrowIceBlockHighlightHandlerSharedEvent
                .checkBlockHighlight(hit, camera);

        // no idea if i actually have to cancel before drawing or not
        // but i kept it like that anyway
        if (result) {
            event.setCanceled(true);

            BlockPos pos = hit.getBlockPos();
            SorrowIceBlockHighlightHandlerSharedEvent
                    .drawCustomOutline(poseStack, camera.getPosition().x,
                            camera.getPosition().y, camera.getPosition().z, buffer, pos);

        }
    }
}
