package dev.maksiks.amaranth;

import dev.maksiks.amaranth.worldgen.biome.ModTerrablenderRegion;
import terrablender.api.TerraBlenderApi;

public class FabricTerrablender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized()
    {
        // hi im blender
        ModTerrablenderRegion.init();
    }
}
