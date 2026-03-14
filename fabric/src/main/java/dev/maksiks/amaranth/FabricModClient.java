package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class FabricModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.MOD_CUTOUT_BLOCKS.forEach((block) -> {
            BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout());
        });
    }
}