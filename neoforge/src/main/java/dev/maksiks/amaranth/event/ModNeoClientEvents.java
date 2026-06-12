package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.entity.ModEntitiesServer;
import dev.maksiks.amaranth.entity.client.ModEntitiesClient;
import dev.maksiks.amaranth.particle.AnthocyaninParticles;
import dev.maksiks.amaranth.particle.ModParticles;
import dev.maksiks.amaranth.particle.SilverBirchParticles;
import dev.maksiks.amaranth.particle.WisteriaParticles;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ModNeoClientEvents {
    @SubscribeEvent
    public static <T extends Entity> void onClientSetup(FMLClientSetupEvent event) {
        ModEntitiesClient.initializeClientEntityData();

        ModEntitiesClient.ENTITY_RENDERERS.forEach((entry) -> {
            // noinspection unchecked
            EntityRenderers.register((EntityType<? extends T>) entry.entity().get(), (EntityRendererProvider<T>) entry.renderer());
        });
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModEntitiesClient.initializeClientEntityData();

        ModEntitiesClient.ENTITY_MODELS.forEach((entry) -> {
            Constants.LOG.info("Ama: all models rhere {}", entry);
            event.registerLayerDefinition(entry.loc(), entry.def());
        });
    }


    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        // absolutely failed to automate this with a Provider reference, so let's just keep it manual
        event.registerSpriteSet(ModParticles.SILVER_BIRCH_PARTICLES.get(), SilverBirchParticles.Provider::new);
        event.registerSpriteSet(ModParticles.ANTHOCYANIN_PARTICLES.get(), AnthocyaninParticles.Provider::new);
        event.registerSpriteSet(ModParticles.WISTERIA_PARTICLES.get(), WisteriaParticles.Provider::new);
    }
}