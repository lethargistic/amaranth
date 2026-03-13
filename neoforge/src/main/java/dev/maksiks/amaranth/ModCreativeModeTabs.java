package dev.maksiks.amaranth;

import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final Supplier<CreativeModeTab> AMARANTH_TAB = CREATIVE_MODE_TAB.register("amaranth_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.MYSTIC_LEAVES.get()))
                    .title(Component.translatable("creativetab.amaranth.amaranth"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // misc
                        output.accept(ModItems.MAFIA_BLOB.get());
                        output.accept(ModItems.BEANIE_BLOB.get());
                        output.accept(ModItems.EMPTY_TEA_CUP.get());
                        output.accept(ModItems.MELON_HELMET.get());

                        // biome items
                        output.accept(ModItems.THORN.get());
                        output.accept(ModItems.CROWN_OF_THORNS.get());

                        // food
                        output.accept(ModItems.HEXFRUIT.get());
                        output.accept(ModItems.MUSHROOM_TEA.get());
                        output.accept(ModItems.WISTERIA_JUICE.get());
                        output.accept(ModItems.REED_BAR.get());

                        // spawn eggs
                        output.accept(ModItems.SHROOM_BOI_SPAWN_EGG.get());

                        // leaves
                        output.accept(ModBlocks.MYSTIC_LEAVES.get());
                        output.accept(ModBlocks.SILVERY_SILVER_BIRCH_LEAVES.get());
                        output.accept(ModBlocks.LIGHT_SILVER_BIRCH_LEAVES.get());
                        output.accept(ModBlocks.DARK_SILVER_BIRCH_LEAVES.get());
                        output.accept(ModBlocks.PURPLE_MIXED_OAK_LEAVES.get());
                        output.accept(ModBlocks.RED_MIXED_OAK_LEAVES.get());
                        output.accept(ModBlocks.YELLOW_MIXED_OAK_LEAVES.get());
                        output.accept(ModBlocks.ANTHOCYANIN_LEAVES.get());
                        output.accept(ModBlocks.BLOOMING_ANTHOCYANIN_LEAVES.get());
                        output.accept(ModBlocks.WISTERIA_LEAVES.get());
                        output.accept(ModBlocks.ALIEN_LEAVES.get());

                        // saplings
                        output.accept(ModBlocks.MYSTIC_SAPLING.get());
                        output.accept(ModBlocks.STUBBY_SAPLING.get());
                        output.accept(ModBlocks.SILVER_BIRCH_SAPLING.get());
                        output.accept(ModBlocks.PURPLE_MIXED_OAK_SAPLING.get());
                        output.accept(ModBlocks.RED_MIXED_OAK_SAPLING.get());
                        output.accept(ModBlocks.YELLOW_MIXED_OAK_SAPLING.get());
                        output.accept(ModBlocks.TRIMMED_TREE_SAPLING.get());
                        output.accept(ModBlocks.ANTHOCYANIN_SAPLING.get());
                        output.accept(ModBlocks.SPEARY_SAPLING.get());
                        output.accept(ModBlocks.WISTERIA_SAPLING.get());
                        output.accept(ModBlocks.RED_MINI_SHROOM_SPORELING.get());
                        output.accept(ModBlocks.BROWN_MINI_SHROOM_SPORELING.get());
                        output.accept(ModBlocks.WITCHY_SAPLING.get());
                        output.accept(ModBlocks.ALPINE_SPRUCE_SAPLING.get());
                        output.accept(ModBlocks.SATISTREE_SAPLING.get());
                        output.accept(ModBlocks.GIGANTIC_SATISTREE_SPROUTS.get());
                        output.accept(ModBlocks.ALIEN_FENCE_PLANT_SAPLING.get());
                        output.accept(ModBlocks.SHRUB_SAPLING.get());

                        // biome decor
                        output.accept(ModBlocks.GOLDEN_LEAF_LITTER.get());
                        output.accept(ModBlocks.SPIKY_ARCHES.get());
                        output.accept(ModBlocks.PHLOX.get());
                        output.accept(ModBlocks.REEDS.get());
                        output.accept(ModBlocks.ALIEN_PHYLLOSTACHYS.get());

                        // flowers
                        output.accept(ModBlocks.MALACHITE_VIPERS_BUGLOSS.get());
                        output.accept(ModBlocks.LUPINE.get());

                        // full biome vegetation blocks
                        output.accept(ModBlocks.THICK_PUMPKIN.get());

                        // woods and stuff
                        output.accept(ModBlocks.MYSTIC_LOG.get());
                        output.accept(ModBlocks.ANTHOCYANIN_LOG.get());
                        output.accept(ModBlocks.JUICY_WISTERIA_LOG.get());
                        output.accept(ModBlocks.WISTERIA_LOG.get());
                        output.accept(ModBlocks.SATISTREE_LOG.get());
                        output.accept(ModBlocks.MYSTIC_WOOD.get());
                        output.accept(ModBlocks.ANTHOCYANIN_WOOD.get());
                        output.accept(ModBlocks.WISTERIA_WOOD.get());
                        output.accept(ModBlocks.SATISTREE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_MYSTIC_LOG.get());
                        output.accept(ModBlocks.STRIPPED_ANTHOCYANIN_LOG.get());
                        output.accept(ModBlocks.STRIPPED_WISTERIA_LOG.get());
                        output.accept(ModBlocks.STRIPPED_SATISTREE_LOG.get());
                        output.accept(ModBlocks.STRIPPED_MYSTIC_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_ANTHOCYANIN_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_WISTERIA_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_SATISTREE_WOOD.get());

                        output.accept(ModBlocks.MYSTIC_PLANKS.get());
                        output.accept(ModBlocks.ANTHOCYANIN_PLANKS.get());
                        output.accept(ModBlocks.WISTERIA_PLANKS.get());
                        output.accept(ModBlocks.SATISTREE_PLANKS.get());

                        output.accept(ModBlocks.MYSTIC_STAIRS.get());
                        output.accept(ModBlocks.ANTHOCYANIN_STAIRS.get());
                        output.accept(ModBlocks.WISTERIA_STAIRS.get());
                        output.accept(ModBlocks.SATISTREE_STAIRS.get());
                        output.accept(ModBlocks.MYSTIC_SLAB.get());
                        output.accept(ModBlocks.ANTHOCYANIN_SLAB.get());
                        output.accept(ModBlocks.WISTERIA_SLAB.get());
                        output.accept(ModBlocks.SATISTREE_SLAB.get());

                        output.accept(ModBlocks.MYSTIC_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.ANTHOCYANIN_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.WISTERIA_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.SATISTREE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.MYSTIC_BUTTON.get());
                        output.accept(ModBlocks.ANTHOCYANIN_BUTTON.get());
                        output.accept(ModBlocks.WISTERIA_BUTTON.get());
                        output.accept(ModBlocks.SATISTREE_BUTTON.get());

                        output.accept(ModBlocks.MYSTIC_FENCE.get());
                        output.accept(ModBlocks.ANTHOCYANIN_FENCE.get());
                        output.accept(ModBlocks.WISTERIA_FENCE.get());
                        output.accept(ModBlocks.SATISTREE_FENCE.get());
                        output.accept(ModBlocks.MYSTIC_FENCE_GATE.get());
                        output.accept(ModBlocks.ANTHOCYANIN_FENCE_GATE.get());
                        output.accept(ModBlocks.WISTERIA_FENCE_GATE.get());
                        output.accept(ModBlocks.SATISTREE_FENCE_GATE.get());
                        output.accept(ModBlocks.ALIEN_FENCE_PLANT.get());

                        output.accept(ModBlocks.MYSTIC_DOOR.get());
                        output.accept(ModBlocks.ANTHOCYANIN_DOOR.get());
                        output.accept(ModBlocks.ORNAMENTED_ANTHOCYANIN_DOOR.get());
                        output.accept(ModBlocks.WISTERIA_DOOR.get());
                        output.accept(ModBlocks.SATISTREE_DOOR.get());
                        output.accept(ModBlocks.MYSTIC_TRAPDOOR.get());
                        output.accept(ModBlocks.ANTHOCYANIN_TRAPDOOR.get());
                        output.accept(ModBlocks.ORNAMENTED_ANTHOCYANIN_TRAPDOOR.get());
                        output.accept(ModBlocks.WISTERIA_TRAPDOOR.get());
                        output.accept(ModBlocks.SATISTREE_TRAPDOOR.get());

                        // rocks
                        output.accept(ModBlocks.SORROW_ICE.get());
                        output.accept(ModBlocks.REMNANT_SORROW_ICE.get());
                        output.accept(ModBlocks.MARBLE.get());
                        output.accept(ModBlocks.VOLCANIC_ASH.get());

                        // music discs
                        output.accept(ModItems.PALETTE_OVERLOAD_MUSIC_DISC.get());
                    }).build());

    // can't forget
    // .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Amaranth.MOD_ID, "amaranth_tab"))
    // to have tabs ordered if i add more


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
