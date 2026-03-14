package dev.maksiks.amaranth.datagen;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static dev.maksiks.amaranth.util.Utils.findBlockId;
import static dev.maksiks.amaranth.util.Utils.findItemId;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // misc
        basicItem(ModItems.PALETTE_OVERLOAD_MUSIC_DISC.get());
        basicItem(ModItems.EMPTY_TEA_CUP.get());
        basicItem(ModItems.MELON_HELMET.get());

        // mystic
        basicItem(ModItems.MAFIA_BLOB.get());
        basicItem(ModItems.BEANIE_BLOB.get());
        basicItem(ModItems.HEXFRUIT.get());

        buttonItem(ModBlocks.MYSTIC_BUTTON, ModBlocks.MYSTIC_PLANKS);
        fenceItem(ModBlocks.MYSTIC_FENCE, ModBlocks.MYSTIC_PLANKS);

        basicItem(ModBlocks.MYSTIC_DOOR.get().asItem());

        splatBlockItem(ModBlocks.MYSTIC_SAPLING);

        // stubby
        splatBlockItem(ModBlocks.STUBBY_SAPLING);

        // silver
        splatBlockItem(ModBlocks.GOLDEN_LEAF_LITTER);
        splatBlockItem(ModBlocks.SILVER_BIRCH_SAPLING);

        // mixed
        splatBlockItem(ModBlocks.PURPLE_MIXED_OAK_SAPLING);
        splatBlockItem(ModBlocks.RED_MIXED_OAK_SAPLING);
        splatBlockItem(ModBlocks.YELLOW_MIXED_OAK_SAPLING);

        // orderly
        splatBlockItem(ModBlocks.TRIMMED_TREE_SAPLING);

        withExistingParent(findItemId(ModItems.SHROOM_BOI_SPAWN_EGG), mcLoc("item/template_spawn_egg"));

        // shroom
        basicItem(ModItems.MUSHROOM_TEA.get());

        // anthocyanin
        buttonItem(ModBlocks.ANTHOCYANIN_BUTTON, ModBlocks.ANTHOCYANIN_PLANKS);
        fenceItem(ModBlocks.ANTHOCYANIN_FENCE, ModBlocks.ANTHOCYANIN_PLANKS);

        basicItem(ModBlocks.ANTHOCYANIN_DOOR.get().asItem());
        basicItem(ModBlocks.ORNAMENTED_ANTHOCYANIN_DOOR.get().asItem());

        splatBlockItem(ModBlocks.ANTHOCYANIN_SAPLING);

        splatBlockItem(ModBlocks.MALACHITE_VIPERS_BUGLOSS);

        // pain
        splatBlockItem(ModBlocks.SPIKY_ARCHES);
        basicItem(ModItems.THORN.get().asItem());

        basicItem(ModItems.CROWN_OF_THORNS.get().asItem());

        // speary
        splatBlockItem(ModBlocks.SPEARY_SAPLING);

        // pastel
        buttonItem(ModBlocks.WISTERIA_BUTTON, ModBlocks.WISTERIA_PLANKS);
        fenceItem(ModBlocks.WISTERIA_FENCE, ModBlocks.WISTERIA_PLANKS);

        basicItem(ModBlocks.WISTERIA_DOOR.get().asItem());

        splatBlockItem(ModBlocks.WISTERIA_SAPLING);

        splatBlockItem(ModBlocks.PHLOX);

        basicItem(ModItems.WISTERIA_JUICE.get());

        // mush
        basicItem(ModBlocks.REEDS.get().asItem());
        basicItem(ModItems.REED_BAR.get());

        splatBlockItem(ModBlocks.RED_MINI_SHROOM_SPORELING);
        splatBlockItem(ModBlocks.BROWN_MINI_SHROOM_SPORELING);

        // witchy
        splatBlockItem(ModBlocks.WITCHY_SAPLING);

        // lupine
        splatBlockItem(ModBlocks.LUPINE);

        // alpine
        splatBlockItem(ModBlocks.ALPINE_SPRUCE_SAPLING);

        // satis
        buttonItem(ModBlocks.SATISTREE_BUTTON, ModBlocks.SATISTREE_PLANKS);
        fenceItem(ModBlocks.SATISTREE_FENCE, ModBlocks.SATISTREE_PLANKS);

        basicItem(ModBlocks.SATISTREE_DOOR.get().asItem());

        splatBlockItem(ModBlocks.SATISTREE_SAPLING);
        splatBlockItem(ModBlocks.GIGANTIC_SATISTREE_SPROUTS);

        basicItem(ModBlocks.ALIEN_PHYLLOSTACHYS.get().asItem());

        fenceItem(ModBlocks.ALIEN_FENCE_PLANT, ModBlocks.ALIEN_FENCE_PLANKS);
        splatBlockItem(ModBlocks.ALIEN_FENCE_PLANT_SAPLING);

        // shrub
        splatBlockItem(ModBlocks.SHRUB_SAPLING);

    }
    private void splatBlockItem(Supplier<Block> block) {
        withExistingParent(findBlockId(block),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + findBlockId(block)));
    }

    private void splatBlockItem(Supplier<Block> block, String appendix) {
        withExistingParent(findBlockId(block),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + findBlockId(block) + appendix));
    }

    public void buttonItem(Supplier<? extends Block> block, Supplier<Block> baseBlock) {
        this.withExistingParent(findBlockId(block), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,
                        "block/" + findBlockId(baseBlock)));
    }

    public void fenceItem(Supplier< ? extends Block> block, Supplier<Block> baseBlock) {
        this.withExistingParent(findBlockId(block), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,
                        "block/" + findBlockId(baseBlock)));
    }
}
