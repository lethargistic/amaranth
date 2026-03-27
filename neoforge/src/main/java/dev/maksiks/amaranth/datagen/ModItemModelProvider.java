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
        ModBlocks.MOD_SAPLINGS.forEach(this::splatBlockItem);

        // misc
        basicItem(ModItems.PALETTE_OVERLOAD_MUSIC_DISC.get());
        basicItem(ModItems.EMPTY_TEA_CUP.get());
        basicItem(ModItems.MELON_HELMET.get());
        basicItem(ModItems.ASHES_OF_THE_LAWN_GNOME.get());

        // mystic
        basicItem(ModItems.MAFIA_BLOB.get());
        basicItem(ModItems.BEANIE_BLOB.get());
        basicItem(ModItems.HEXFRUIT.get());

        buttonItem(ModBlocks.MYSTIC_BUTTON, ModBlocks.MYSTIC_PLANKS);
        fenceItem(ModBlocks.MYSTIC_FENCE, ModBlocks.MYSTIC_PLANKS);

        basicItem(ModBlocks.MYSTIC_DOOR.get().asItem());

        // silver
        splatBlockItem(ModBlocks.GOLDEN_LEAF_LITTER);

        withExistingParent(findItemId(ModItems.SHROOM_BOI_SPAWN_EGG), mcLoc("item/template_spawn_egg"));

        // shroom
        basicItem(ModItems.MUSHROOM_TEA.get());

        // anthocyanin
        buttonItem(ModBlocks.ANTHOCYANIN_BUTTON, ModBlocks.ANTHOCYANIN_PLANKS);
        fenceItem(ModBlocks.ANTHOCYANIN_FENCE, ModBlocks.ANTHOCYANIN_PLANKS);

        basicItem(ModBlocks.ANTHOCYANIN_DOOR.get().asItem());
        basicItem(ModBlocks.ORNAMENTED_ANTHOCYANIN_DOOR.get().asItem());

        splatBlockItem(ModBlocks.MALACHITE_VIPERS_BUGLOSS);

        // pain
        splatBlockItem(ModBlocks.SPIKY_ARCHES);
        basicItem(ModItems.THORN.get().asItem());

        basicItem(ModItems.CROWN_OF_THORNS.get().asItem());

        // pastel
        buttonItem(ModBlocks.WISTERIA_BUTTON, ModBlocks.WISTERIA_PLANKS);
        fenceItem(ModBlocks.WISTERIA_FENCE, ModBlocks.WISTERIA_PLANKS);

        basicItem(ModBlocks.WISTERIA_DOOR.get().asItem());

        splatBlockItem(ModBlocks.PHLOX);

        basicItem(ModItems.WISTERIA_JUICE.get());

        // mush
        basicItem(ModBlocks.REEDS.get().asItem());
        basicItem(ModItems.REED_BAR.get());

        // lupine
        splatBlockItem(ModBlocks.LUPINE);

        // satis
        buttonItem(ModBlocks.SATISTREE_BUTTON, ModBlocks.SATISTREE_PLANKS);
        fenceItem(ModBlocks.SATISTREE_FENCE, ModBlocks.SATISTREE_PLANKS);

        basicItem(ModBlocks.SATISTREE_DOOR.get().asItem());

        basicItem(ModBlocks.ALIEN_PHYLLOSTACHYS.get().asItem());

        fenceItem(ModBlocks.ALIEN_FENCE_PLANT, ModBlocks.ALIEN_FENCE_PLANKS);

        // bleed
        basicItem(ModBlocks.CRESSET_FLOWER.get().asItem());
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
