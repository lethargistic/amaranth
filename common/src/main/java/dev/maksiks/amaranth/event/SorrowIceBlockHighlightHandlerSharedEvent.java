package dev.maksiks.amaranth.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.maksiks.amaranth.block.ModBlocks;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Matrix4f;
import org.joml.Vector3f;


// technically fixes a vanilla bug on translucent blocks where you can kinda xray a little through the highlight
// it's not a big deal on vanilla ice cause it's light but on my ice it was highly visible
// i remember this was a pain to make and ended up pretty bad
// edit: it looks good just as the vanilla one what is my inner monologue talking about
// edit 2: this was such an overkill perfectionistic thing why did i do it, but it looks cool
public class SorrowIceBlockHighlightHandlerSharedEvent {
    public static boolean checkBlockHighlight(BlockHitResult hit, Camera camera) {
        BlockPos pos = hit.getBlockPos();

        Level level = camera.getEntity().level();
        BlockState state = level.getBlockState(pos);

        return state.is(ModBlocks.SORROW_ICE.get()) || state.is(ModBlocks.REMNANT_SORROW_ICE.get());
    }


    public static void drawCustomOutline(PoseStack poseStack, double camX, double camY, double camZ, MultiBufferSource buffer, BlockPos pos) {
        poseStack.pushPose();
        poseStack.translate(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);

        VertexConsumer consumer = buffer.getBuffer(RenderType.lines());
        drawCubeOutline(poseStack, consumer);

        poseStack.popPose();
    }

    private static void drawCubeOutline(PoseStack poseStack, VertexConsumer consumer) {
        Matrix4f mat = poseStack.last().pose();

        Vector3f[] corners = new Vector3f[]{
                new Vector3f(0f, 0f, 0f), new Vector3f(1f, 0f, 0f), new Vector3f(1f, 1f, 0f), new Vector3f(0f, 1f, 0f),
                new Vector3f(0f, 0f, 1f), new Vector3f(1f, 0f, 1f), new Vector3f(1f, 1f, 1f), new Vector3f(0f, 1f, 1f)
        };

        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        float r = 0.2f, g = 0.2f, b = 0.2f, a = 1.0f;

        Vector3f normal = new Vector3f(0, 1, 0);

        for (int[] edge : edges) {
            Vector3f start = corners[edge[0]];
            Vector3f end = corners[edge[1]];

            consumer.addVertex(mat, start.x(), start.y(), start.z())
                    .setColor(r, g, b, a)
                    .setNormal(normal.x(), normal.y(), normal.z());

            consumer.addVertex(mat, end.x(), end.y(), end.z())
                    .setColor(r, g, b, a)
                    .setNormal(normal.x(), normal.y(), normal.z());
        }
    }
}
