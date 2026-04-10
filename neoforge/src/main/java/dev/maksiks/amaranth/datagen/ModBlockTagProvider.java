package dev.maksiks.amaranth.datagen;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    // TODO: refactor it all with ModBlocks utils and maps for everything
    // generic rather than forgetting to add this every time

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // groups

        ModBlocks.MOD_LEAVES.forEach(pair -> {
            Supplier<Block> leaves = pair.getFirst();

            if (leaves == null) return;
            this.tag(BlockTags.LEAVES).add(leaves.get());
        });
        ModBlocks.MOD_SAPLINGS.forEach(sapling -> {
            this.tag(BlockTags.SAPLINGS).add(sapling.get());

        });
        ModBlocks.MOD_FLOWER_POTS.forEach(data -> {
            Supplier<FlowerPotBlock> pot = data.pot();

            if (pot == null) return;
            this.tag(BlockTags.FLOWER_POTS).add(pot.get());
        });

        this.tag(BlockTags.MINEABLE_WITH_HOE).addTag(BlockTags.LEAVES);

        // misc
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MARBLE.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.MARBLE.get());


        this.tag(ModTags.Blocks.NEEDS_SKEWER_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL);

        this.tag(ModTags.Blocks.INCORRECT_FOR_SKEWER_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_SKEWER_TOOL);

        // mystic
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.MYSTIC_LOG.get())
                .add(ModBlocks.MYSTIC_WOOD.get())
                .add(ModBlocks.STRIPPED_MYSTIC_LOG.get())
                .add(ModBlocks.STRIPPED_MYSTIC_WOOD.get());
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.MYSTIC_LOG.get())
                .add(ModBlocks.MYSTIC_WOOD.get())
                .add(ModBlocks.STRIPPED_MYSTIC_LOG.get())
                .add(ModBlocks.STRIPPED_MYSTIC_WOOD.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.MYSTIC_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.MYSTIC_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.MYSTIC_STAIRS.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.MYSTIC_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.MYSTIC_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.MYSTIC_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.MYSTIC_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.MYSTIC_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.MYSTIC_TRAPDOOR.get());

        // desolate
        this.tag(BlockTags.ICE).add(ModBlocks.SORROW_ICE.get());
        this.tag(BlockTags.ICE).add(ModBlocks.REMNANT_SORROW_ICE.get());

        // anthocyanin
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ANTHOCYANIN_LOG.get())
                .add(ModBlocks.ANTHOCYANIN_WOOD.get())
                .add(ModBlocks.STRIPPED_ANTHOCYANIN_LOG.get())
                .add(ModBlocks.STRIPPED_ANTHOCYANIN_WOOD.get());
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.ANTHOCYANIN_LOG.get())
                .add(ModBlocks.ANTHOCYANIN_WOOD.get())
                .add(ModBlocks.STRIPPED_ANTHOCYANIN_LOG.get())
                .add(ModBlocks.STRIPPED_ANTHOCYANIN_WOOD.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.ANTHOCYANIN_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.ANTHOCYANIN_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.ANTHOCYANIN_STAIRS.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.ANTHOCYANIN_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.ANTHOCYANIN_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.ANTHOCYANIN_DOOR.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.ORNAMENTED_ANTHOCYANIN_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.ANTHOCYANIN_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.ORNAMENTED_ANTHOCYANIN_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.ANTHOCYANIN_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.ANTHOCYANIN_FENCE_GATE.get());

        this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.MALACHITE_VIPERS_BUGLOSS.get());

        // thrumletons
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.THICK_PUMPKIN.get());

        // spiky
        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(ModBlocks.SPIKY_ARCHES.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.SPIKY_ARCHES.get());

        // pastel
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WISTERIA_LOG.get())
                .add(ModBlocks.WISTERIA_WOOD.get())
                .add(ModBlocks.STRIPPED_WISTERIA_LOG.get())
                .add(ModBlocks.STRIPPED_WISTERIA_WOOD.get());
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.JUICY_WISTERIA_LOG.get())
                .add(ModBlocks.WISTERIA_LOG.get())
                .add(ModBlocks.WISTERIA_WOOD.get())
                .add(ModBlocks.STRIPPED_WISTERIA_LOG.get())
                .add(ModBlocks.STRIPPED_WISTERIA_WOOD.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.WISTERIA_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.WISTERIA_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.WISTERIA_STAIRS.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.WISTERIA_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.WISTERIA_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.WISTERIA_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.WISTERIA_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.WISTERIA_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.WISTERIA_FENCE_GATE.get());

        // lupine
        this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.LUPINE.get());

        // ashen
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.VOLCANIC_ASH.get());

        // satis
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.SATISTREE_LOG.get())
                .add(ModBlocks.SATISTREE_WOOD.get())
                .add(ModBlocks.STRIPPED_SATISTREE_LOG.get())
                .add(ModBlocks.STRIPPED_SATISTREE_WOOD.get());
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.SATISTREE_LOG.get())
                .add(ModBlocks.SATISTREE_WOOD.get())
                .add(ModBlocks.STRIPPED_SATISTREE_LOG.get())
                .add(ModBlocks.STRIPPED_SATISTREE_WOOD.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.SATISTREE_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.SATISTREE_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.SATISTREE_STAIRS.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.SATISTREE_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.SATISTREE_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.SATISTREE_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.SATISTREE_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.SATISTREE_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.SATISTREE_FENCE_GATE.get());
        this.tag(ModTags.Blocks.ALIEN_PHYLLOSTACHYS_PLANTABLE_ON)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.DIRT).remove(Blocks.COARSE_DIRT)
                .add(ModBlocks.ALIEN_PHYLLOSTACHYS.get(), ModBlocks.ALIEN_PHYLLOSTACHYS_SAPLING.get(), Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL);
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.ALIEN_PHYLLOSTACHYS.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.ALIEN_FENCE_PLANKS.get());
        // sorta wooden? idk, still counts
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.ALIEN_FENCE_PLANT.get());

        // bleed
        this.tag(BlockTags.FLOWERS).add(ModBlocks.CRESSET_FLOWER.get());
    }
}