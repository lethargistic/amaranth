package dev.maksiks.amaranth;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.component.ModDataComponentTypes;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.particle.ModParticles;
import dev.maksiks.amaranth.sound.ModSounds;
import dev.maksiks.amaranth.worldgen.features.ModFeatures;
import dev.maksiks.amaranth.worldgen.features.structure_processor.ModStructureProcessorTypes;
import dev.maksiks.amaranth.worldgen.tree.foliage_placer.ModFoliagePlacerTypes;
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.ModTrunkPlacerTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.core.registries.BuiltInRegistries.STRUCTURE_PROCESSOR;

public class NeoModRegistries {
    public static void register(IEventBus eventBus) {
        DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
        DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);
        DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);
        DeferredRegister<EntityType<?>> ENTITY_TYPES =
                DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);
        DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
                DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, Constants.MOD_ID);
        DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
                DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Constants.MOD_ID);
        DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
                DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Constants.MOD_ID);
        DeferredRegister<SoundEvent> SOUND_EVENTS =
                DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);
        DeferredRegister<Feature<?>> FEATURES =
                DeferredRegister.create(BuiltInRegistries.FEATURE, Constants.MOD_ID);
        DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES =
                DeferredRegister.create(STRUCTURE_PROCESSOR, Constants.MOD_ID);
        DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
                DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        CREATIVE_MODE_TABS.register(ModCreativeTabs.MAIN_TAB_NAME, ModCreativeTabs.loaderTabContents);

        ModBlocks.BLOCK_MAP.forEach(BLOCKS::register);
        ModItems.ITEM_MAP.forEach(ITEMS::register);
        ModEntities.ENTITY_TYPE_MAP.forEach(ENTITY_TYPES::register);
        ModTrunkPlacerTypes.TRUNK_PLACER_TYPE_MAP.forEach(TRUNK_PLACER_TYPES::register);
        ModFoliagePlacerTypes.FOLIAGE_PLACER_TYPE_MAP.forEach(FOLIAGE_PLACER_TYPES::register);
        ModParticles.PARTICLE_TYPE_MAP.forEach(PARTICLE_TYPES::register);
        ModSounds.SOUND_EVENT_MAP.forEach(SOUND_EVENTS::register);
        ModFeatures.FEATURE_MAP.forEach(FEATURES::register);
        ModStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPE_MAP.forEach(STRUCTURE_PROCESSOR_TYPES::register);
        ModDataComponentTypes.DATA_COMPONENT_TYPE_MAP.forEach(DATA_COMPONENT_TYPES::register);

        CREATIVE_MODE_TABS.register(eventBus);
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        ENTITY_TYPES.register(eventBus);
        TRUNK_PLACER_TYPES.register(eventBus);
        FOLIAGE_PLACER_TYPES.register(eventBus);
        PARTICLE_TYPES.register(eventBus);
        SOUND_EVENTS.register(eventBus);
        FEATURES.register(eventBus);
        STRUCTURE_PROCESSOR_TYPES.register(eventBus);
    }
}
