package dev.maksiks.amaranth;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.platform.Services;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {
    public static final List<Supplier<? extends ItemLike>> MAIN_CREATIVE_MODE_ITEM_MAP = new ArrayList<>();

    static {

        // misc
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.MAFIA_BLOB);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.BEANIE_BLOB);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.EMPTY_TEA_CUP);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.MELON_HELMET);

        // biome items
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.THORN);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.CROWN_OF_THORNS);

        // food
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.HEXFRUIT);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.MUSHROOM_TEA);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.WISTERIA_JUICE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.REED_BAR);

        // spawn eggs
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.SHROOM_BOI_SPAWN_EGG);

        // leaves
        ModBlocks.MOD_LEAVES.forEach((pair) -> {
            Supplier<Block> leaves = pair.getFirst();
            MAIN_CREATIVE_MODE_ITEM_MAP.add(leaves);
        });

        // saplings
        MAIN_CREATIVE_MODE_ITEM_MAP.addAll(ModBlocks.MOD_SAPLINGS);

        // biome decor
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.GOLDEN_LEAF_LITTER);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SPIKY_ARCHES);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.PHLOX);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.REEDS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ALIEN_PHYLLOSTACHYS);

        // flowers
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MALACHITE_VIPERS_BUGLOSS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.LUPINE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.CRESSET_FLOWER);

        // full biome vegetation blocks
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.THICK_PUMPKIN);

        // woods and stuff
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.JUICY_WISTERIA_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_MYSTIC_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_ANTHOCYANIN_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_WISTERIA_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_SATISTREE_LOG);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_MYSTIC_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_ANTHOCYANIN_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_WISTERIA_WOOD);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.STRIPPED_SATISTREE_WOOD);

        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_PLANKS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_PLANKS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_PLANKS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_PLANKS);

        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_STAIRS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_STAIRS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_STAIRS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_STAIRS);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_SLAB);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_SLAB);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_SLAB);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_SLAB);

        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_PRESSURE_PLATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_PRESSURE_PLATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_PRESSURE_PLATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_PRESSURE_PLATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_BUTTON);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_BUTTON);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_BUTTON);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_BUTTON);

        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_FENCE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_FENCE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_FENCE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_FENCE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_FENCE_GATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_FENCE_GATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_FENCE_GATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_FENCE_GATE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ALIEN_FENCE_PLANT);

        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_DOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_DOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ORNAMENTED_ANTHOCYANIN_DOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_DOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_DOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MYSTIC_TRAPDOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ANTHOCYANIN_TRAPDOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.ORNAMENTED_ANTHOCYANIN_TRAPDOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WISTERIA_TRAPDOOR);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SATISTREE_TRAPDOOR);

        // rocks
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.SORROW_ICE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.REMNANT_SORROW_ICE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.MARBLE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.VOLCANIC_ASH);

        // music discs
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.PALETTE_OVERLOAD_MUSIC_DISC);

        // misc but at the end
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModItems.ASHES_OF_THE_LAWN_GNOME);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WORLDGEN_MARKER_RED);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WORLDGEN_MARKER_BLUE);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WORLDGEN_MARKER_GREEN);
        MAIN_CREATIVE_MODE_ITEM_MAP.add(ModBlocks.WORLDGEN_MARKER_PURPLE);
    }

    // can't forget
    // .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Amaranth.MOD_ID, "amaranth_tab"))
    // to have tabs ordered if i add more

    public static final String MAIN_TAB_NAME = "amaranth_tab";
    public static final Supplier<CreativeModeTab> loaderTabContents = Services.PLATFORM.createCreativeTab(MAIN_TAB_NAME, MAIN_CREATIVE_MODE_ITEM_MAP, ModBlocks.MYSTIC_LEAVES);

    public static void init() {
        // besummon your eyeballs
    }
}
