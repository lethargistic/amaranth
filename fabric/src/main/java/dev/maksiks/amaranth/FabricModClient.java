package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.particle.AnthocyaninParticles;
import dev.maksiks.amaranth.particle.ModParticles;
import dev.maksiks.amaranth.particle.SilverBirchParticles;
import dev.maksiks.amaranth.particle.WisteriaParticles;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.fml.config.ModConfig;

@Environment(EnvType.CLIENT)
public class FabricModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // render types
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

        // particles

        // absolutely failed to automate this with a Provider reference, so let's just keep it manual
        ParticleFactoryRegistry.getInstance().register(ModParticles.SILVER_BIRCH_PARTICLES.get(), SilverBirchParticles.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ANTHOCYANIN_PARTICLES.get(), AnthocyaninParticles.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WISTERIA_PARTICLES.get(), WisteriaParticles.Provider::new);

        // config
        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.CLIENT, ClientConfig.SPEC, "amaranth/amaranth-client.toml");

        // Entity renderers
        ModEntities.ENTITY_RENDERERS.forEach((entry) -> {
            // noinspection unchecked
            EntityRendererProvider<Entity> r = (EntityRendererProvider<Entity>) entry.renderer();
            EntityRenderers.register((EntityType<? extends Entity>) entry.entity().get(), r);
        });
    }
}