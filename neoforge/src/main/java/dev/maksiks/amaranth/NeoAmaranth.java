package dev.maksiks.amaranth;


import com.mojang.logging.LogUtils;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.entity.client.ShroomBoiRenderer;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.particle.ModParticles;
import dev.maksiks.amaranth.sound.ModSounds;
import dev.maksiks.amaranth.worldgen.biome.ModTerrablenderRegion;
import dev.maksiks.amaranth.worldgen.biome.surface.ModSurfaceRules;
import dev.maksiks.amaranth.worldgen.features.ModFeatures;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

@Mod(Constants.MOD_ID)
public class NeoAmaranth {
    public static final String MOD_ID = "amaranth";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NeoAmaranth(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (amaranth) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        NeoModRegistries.register(modEventBus);

        ModTerrablenderRegion.init();
        
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC, "amaranth/amaranth-common.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "amaranth/amaranth-client.toml");

        CommonClass.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.MAFIA_BLOB.get());
            event.accept(ModItems.BEANIE_BLOB.get());
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.SHROOM_BOI_SPAWN_EGG.get());
        }

        // not putting much stuff into vanilla tabs because you can just find it in the mod tab
        // and thus less thinking for me

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.HEXFRUIT.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // TODO: remove this when they get mad at me for spamming the log
        LOGGER.info("TODO: remove this when they get mad at me for spamming the log");
        LOGGER.info("""
           
           (\\ __ /)
             (UwU)   ✧
          ＿ノ ヽ ノ＼＿ 
        /　`/ ⌒Ｙ⌒ Ｙ \\ \\ '\\
      ( 　 (三ヽ人　   /　|  |
     |　ﾉ⌒＼ ￣￣ヽ　 ノ  |
      ヽ＿＿＿＞､＿＿／'
          ｜( 王 ﾉ〈 
           /ﾐ`ー―彡\\ 
          |╰      ╯|   
          |   /\\   |
          |  /  \\  |
          | /    \\ |
""");
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SHROOM_BOI.get(), ShroomBoiRenderer::new);
        }
    }
}
