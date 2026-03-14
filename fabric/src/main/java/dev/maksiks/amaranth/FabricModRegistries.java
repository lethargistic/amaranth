package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.pathfinder.PathType;

public class FabricModRegistries {
    public static void init() {
        // TODO fabrec: cook every registry on fabric
        // TODO fabrec: check if everything flammable on fabric
        // TODO fabrec: figure out how to make everything burnable in furnance on fabric
        LandPathNodeTypesRegistry.register(ModBlocks.SPIKY_ARCHES.get(), PathType.DAMAGE_OTHER, null);

        ModBlocks.FABRIC_MOD_FLAMMABLE_BLOCKS.forEach((block, data) ->
                FlammableBlockRegistry.getDefaultInstance().add(block.get(), data.burn(), data.spread()));
        ModBlocks.MOD_STRIPPABLES.forEach((strippable, stripped) ->
                StrippableBlockRegistry.register(strippable.get(), stripped.get()));
    }
}
