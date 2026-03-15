package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.fml.config.ModConfig;

public class FabricAmaranth implements ModInitializer {
    
    @Override
    public void onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        CommonClass.init();

        FabricModRegistries.init();

        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, Config.SPEC, "amaranth/amaranth-common.toml");
    }
}
