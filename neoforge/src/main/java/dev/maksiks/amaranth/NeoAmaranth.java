package dev.maksiks.amaranth;


import dev.maksiks.amaranth.datagen.DataGenerators;
import dev.maksiks.amaranth.worldgen.biome.ModTerrablenderRegion;
import dev.maksiks.amaranth.worldgen.biome.surface.ModSurfaceRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.SurfaceRuleManager;

@Mod(Constants.MOD_ID)
public class NeoAmaranth {
    public static final String MOD_ID = "amaranth";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NeoAmaranth(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (amaranth) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        NeoModRegistries.register(modEventBus);

        ModTerrablenderRegion.init();

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC, "amaranth/amaranth-common.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "amaranth/amaranth-client.toml");

        CommonClass.init();

        // because it doesn't see the annotation apparently
        modEventBus.register(DataGenerators.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
    }

    // TODO maybe: add to vanilla tabs
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        //
    }
}
