package dev.maksiks.amaranth;

import dev.maksiks.amaranth.event.ModFabricEvents;
import dev.maksiks.amaranth.worldgen.biome.surface.ModSurfaceRules;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;
import terrablender.api.SurfaceRuleManager;

public class FabricAmaranth implements ModInitializer {
    
    @Override
    public void onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        CommonClass.init();

        FabricModRegistries.init();

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Constants.MOD_ID, ModSurfaceRules.makeRules());

        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, Config.SPEC, "amaranth/amaranth-common.toml");

        ModFabricEvents.init();
    }
}
