package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import static dev.maksiks.amaranth.block.ModBlocks.MOD_FLOWER_POTS;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ModOtherEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModEntities.ENTITY_MODELS.forEach((entry) -> {
            event.registerLayerDefinition(entry.loc(), entry.def());
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // noinspection unchecked
        ModEntities.ENTITY_ATTRIBUTES.forEach((entry)
                -> event.put((EntityType<? extends LivingEntity>) entry.entity().get(), entry.attributes().get().build()));
    }

    // note currently not on fabric since i dont use it yet anyway
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.SHROOM_BOI.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, reason, pos, random) -> {
                    return true;
                },
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // note: fairly certain you don't need to do this on fabric (it works)
        // but im confused as to why you do on Neo then
        event.enqueueWork(() -> MOD_FLOWER_POTS.forEach((plant, pot) -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, Utils.findBlockId(plant)),
                    pot
            );
        }));
    }
}