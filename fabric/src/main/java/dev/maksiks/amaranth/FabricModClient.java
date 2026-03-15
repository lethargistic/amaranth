package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.fml.config.ModConfig;

@Environment(EnvType.CLIENT)
public class FabricModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.MOD_CUTOUT_BLOCKS.forEach((block) -> {
            assert block != null;
            BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout());
        });
        ModBlocks.MOD_CUTOUT_MIPPED_BLOCKS.forEach((block) -> {
            assert block != null;
            BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutoutMipped());
        });
        ModBlocks.MOD_TRANSLUCENT_BLOCKS.forEach((block) -> {
            assert block != null;
            BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.translucent());
        });

        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.CLIENT, ClientConfig.SPEC, "amaranth/amaranth-client.toml"); }

}