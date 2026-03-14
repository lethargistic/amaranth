package dev.maksiks.amaranth;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.particle.ModParticles;
import dev.maksiks.amaranth.sound.ModSounds;
import dev.maksiks.amaranth.worldgen.features.ModFeatures;
import dev.maksiks.amaranth.worldgen.features.structure_processor.ModStructureProcessorTypes;
import dev.maksiks.amaranth.worldgen.tree.foliage_placer.ModFoliagePlacerTypes;
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.ModTrunkPlacerTypes;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.pathfinder.PathType;

import java.util.HashMap;

public class FabricModRegistries {
    public static void init() {
        // TODO fabrec: cook every registry on fabric
        // TODO fabrec: check if everything flammable on fabric
        // TODO fabrec: figure out how to make everything burnable in furnance on fabric

        // creative tabs register themselves because life is cruel
        ModCreativeTabs.init();
        registerUsualEach(ModBlocks.BLOCK_MAP, BuiltInRegistries.BLOCK);
        registerUsualEach(ModItems.ITEM_MAP, BuiltInRegistries.ITEM);
        registerUsualEach(ModEntities.ENTITY_TYPE_MAP, BuiltInRegistries.ENTITY_TYPE);
        registerUsualEach(ModTrunkPlacerTypes.TRUNK_PLACER_TYPE_MAP, BuiltInRegistries.TRUNK_PLACER_TYPE);

        registerUsualEach(ModFoliagePlacerTypes.FOLIAGE_PLACER_TYPE_MAP, BuiltInRegistries.FOLIAGE_PLACER_TYPE);
        registerUsualEach(ModParticles.PARTICLE_TYPE_MAP, BuiltInRegistries.PARTICLE_TYPE);
        registerUsualEach(ModSounds.SOUND_EVENT_MAP, BuiltInRegistries.SOUND_EVENT);
        registerUsualEach(ModFeatures.FEATURE_MAP, BuiltInRegistries.FEATURE);
        registerUsualEach(ModStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPE_MAP, BuiltInRegistries.STRUCTURE_PROCESSOR);

        // Fabric specific
        LandPathNodeTypesRegistry.register(ModBlocks.SPIKY_ARCHES.get(), PathType.DAMAGE_OTHER, null);

        ModBlocks.FABRIC_MOD_FLAMMABLE_BLOCKS.forEach((block, data) ->
                FlammableBlockRegistry.getDefaultInstance().add(block.get(), data.burn(), data.spread()));
        ModBlocks.MOD_STRIPPABLES.forEach((strippable, stripped) ->
                StrippableBlockRegistry.register(strippable.get(), stripped.get()));
    }

    private static <T> void registerUsual(String path, Supplier<T> thing, Registry<? super T> registry) {
        Registry.register(registry, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path), thing.get());
    }

    private static <T, R extends Registry<? super T>> void registerUsualEach(HashMap<String, ? extends Supplier<? extends T>> things, R registry) {
        things.forEach((String path, Supplier<? extends T> thing) -> {
            registerUsual(path, thing, registry);
        });
    }
}
