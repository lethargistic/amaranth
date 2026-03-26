package dev.maksiks.amaranth.datagen;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.block.custom.SpikyArchesBlock;
import dev.maksiks.amaranth.util.Utils;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // stores

        ModBlocks.MOD_LEAVES.forEach(pair -> {
            Supplier<Block> leaves = pair.getFirst();
            if (leaves == null) return;
            leavesBlock(leaves);
        });
        ModBlocks.MOD_SAPLINGS.forEach(this::twoPlanesCutoutBlock);
        ModBlocks.MOD_FLOWER_POTS.forEach(data -> {
            Supplier<FlowerPotBlock> pot = data.pot();
            Supplier<Block> plant = data.plant();

            if (!data.autoModel()) {
                return;
            }

            if (plant == null) return;
            pottedPlantBlock(pot, plant);
        });

        // misc
        blockWithItem(ModBlocks.MARBLE);

        // mystic
        logBlock((ModBlocks.MYSTIC_LOG.get()));
        axisBlock((ModBlocks.MYSTIC_WOOD.get()), blockTexture(ModBlocks.MYSTIC_LOG.get()), blockTexture(ModBlocks.MYSTIC_LOG.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MYSTIC_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MYSTIC_WOOD.get()), blockTexture(ModBlocks.STRIPPED_MYSTIC_LOG.get()), blockTexture(ModBlocks.STRIPPED_MYSTIC_LOG.get()));

        blockItem(ModBlocks.MYSTIC_LOG);
        blockItem(ModBlocks.MYSTIC_WOOD);
        blockItem(ModBlocks.STRIPPED_MYSTIC_LOG);
        blockItem(ModBlocks.STRIPPED_MYSTIC_WOOD);

        blockWithItem(ModBlocks.MYSTIC_PLANKS);

        stairsBlock(ModBlocks.MYSTIC_STAIRS.get(), blockTexture(ModBlocks.MYSTIC_PLANKS.get()));
        slabBlock(ModBlocks.MYSTIC_SLAB.get(), blockTexture(ModBlocks.MYSTIC_PLANKS.get()), blockTexture(ModBlocks.MYSTIC_PLANKS.get()));

        buttonBlock(ModBlocks.MYSTIC_BUTTON.get(), blockTexture(ModBlocks.MYSTIC_PLANKS.get()));
        pressurePlateBlock(ModBlocks.MYSTIC_PRESSURE_PLATE.get(), blockTexture(ModBlocks.MYSTIC_PLANKS.get()));

        fenceBlock(ModBlocks.MYSTIC_FENCE.get(), blockTexture(ModBlocks.MYSTIC_PLANKS.get()));
        fenceGateBlock(ModBlocks.MYSTIC_FENCE_GATE.get(), blockTexture(ModBlocks.MYSTIC_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.MYSTIC_DOOR.get(), modLoc("block/mystic_door_bottom"), modLoc("block/mystic_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.MYSTIC_TRAPDOOR.get(), modLoc("block/mystic_trapdoor"), true, "cutout");

        blockItem(ModBlocks.MYSTIC_STAIRS);
        blockItem(ModBlocks.MYSTIC_SLAB);
        blockItem(ModBlocks.MYSTIC_PRESSURE_PLATE);
        blockItem(ModBlocks.MYSTIC_FENCE_GATE);
        blockItem(ModBlocks.MYSTIC_TRAPDOOR, "_bottom");

        // leaf litter is made manually

        // desolate
        iceBlock(ModBlocks.SORROW_ICE);
        blockItem(ModBlocks.SORROW_ICE);
        blockWithItem(ModBlocks.REMNANT_SORROW_ICE);

        // anthocyanin
        logBlock((ModBlocks.ANTHOCYANIN_LOG.get()));
        axisBlock((ModBlocks.ANTHOCYANIN_WOOD.get()), blockTexture(ModBlocks.ANTHOCYANIN_LOG.get()), blockTexture(ModBlocks.ANTHOCYANIN_LOG.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ANTHOCYANIN_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ANTHOCYANIN_WOOD.get()), blockTexture(ModBlocks.STRIPPED_ANTHOCYANIN_LOG.get()), blockTexture(ModBlocks.STRIPPED_ANTHOCYANIN_LOG.get()));

        blockItem(ModBlocks.ANTHOCYANIN_LOG);
        blockItem(ModBlocks.ANTHOCYANIN_WOOD);
        blockItem(ModBlocks.STRIPPED_ANTHOCYANIN_LOG);
        blockItem(ModBlocks.STRIPPED_ANTHOCYANIN_WOOD);

        blockWithItem(ModBlocks.ANTHOCYANIN_PLANKS);

        stairsBlock(ModBlocks.ANTHOCYANIN_STAIRS.get(), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()));
        slabBlock(ModBlocks.ANTHOCYANIN_SLAB.get(), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()));

        buttonBlock(ModBlocks.ANTHOCYANIN_BUTTON.get(), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()));
        pressurePlateBlock(ModBlocks.ANTHOCYANIN_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()));

        fenceBlock(ModBlocks.ANTHOCYANIN_FENCE.get(), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()));
        fenceGateBlock(ModBlocks.ANTHOCYANIN_FENCE_GATE.get(), blockTexture(ModBlocks.ANTHOCYANIN_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.ANTHOCYANIN_DOOR.get(), modLoc("block/anthocyanin_door_bottom"), modLoc("block/anthocyanin_door_top"), "cutout");
        doorBlockWithRenderType(ModBlocks.ORNAMENTED_ANTHOCYANIN_DOOR.get(), modLoc("block/ornamented_anthocyanin_door_bottom"), modLoc("block/ornamented_anthocyanin_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.ANTHOCYANIN_TRAPDOOR.get(), modLoc("block/anthocyanin_trapdoor"), true, "cutout");
        trapdoorBlockWithRenderType(ModBlocks.ORNAMENTED_ANTHOCYANIN_TRAPDOOR.get(), modLoc("block/ornamented_anthocyanin_trapdoor"), true, "cutout");

        blockItem(ModBlocks.ANTHOCYANIN_STAIRS);
        blockItem(ModBlocks.ANTHOCYANIN_SLAB);
        blockItem(ModBlocks.ANTHOCYANIN_PRESSURE_PLATE);
        blockItem(ModBlocks.ANTHOCYANIN_FENCE_GATE);
        blockItem(ModBlocks.ANTHOCYANIN_TRAPDOOR, "_bottom");
        blockItem(ModBlocks.ORNAMENTED_ANTHOCYANIN_TRAPDOOR, "_bottom");

        twoPlanesCutoutBlock(ModBlocks.MALACHITE_VIPERS_BUGLOSS);

        // pain
        randomVariantTwoPlaneCutout(ModBlocks.SPIKY_ARCHES, 4);

        // thrumletons
        // TODO: more blockstates
        thickPumpkinBlock(ModBlocks.THICK_PUMPKIN);
        thickPumpkinBlockItem(ModBlocks.THICK_PUMPKIN);

        // pastel
        axisBlock(ModBlocks.JUICY_WISTERIA_LOG.get(), modLoc("block/wisteria_log"), modLoc("block/juicy_wisteria_log_top"));
        logBlock(ModBlocks.WISTERIA_LOG.get());
        axisBlock((ModBlocks.WISTERIA_WOOD.get()), blockTexture(ModBlocks.WISTERIA_LOG.get()), blockTexture(ModBlocks.WISTERIA_LOG.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_WISTERIA_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_WISTERIA_WOOD.get()), blockTexture(ModBlocks.STRIPPED_WISTERIA_LOG.get()), blockTexture(ModBlocks.STRIPPED_WISTERIA_LOG.get()));

        blockItem(ModBlocks.JUICY_WISTERIA_LOG);
        blockItem(ModBlocks.WISTERIA_LOG);
        blockItem(ModBlocks.WISTERIA_WOOD);
        blockItem(ModBlocks.STRIPPED_WISTERIA_LOG);
        blockItem(ModBlocks.STRIPPED_WISTERIA_WOOD);

        blockWithItem(ModBlocks.WISTERIA_PLANKS);

        stairsBlock(ModBlocks.WISTERIA_STAIRS.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));
        slabBlock(ModBlocks.WISTERIA_SLAB.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));

        buttonBlock(ModBlocks.WISTERIA_BUTTON.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));
        pressurePlateBlock(ModBlocks.WISTERIA_PRESSURE_PLATE.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));

        fenceBlock(ModBlocks.WISTERIA_FENCE.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));
        fenceGateBlock(ModBlocks.WISTERIA_FENCE_GATE.get(), blockTexture(ModBlocks.WISTERIA_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.WISTERIA_DOOR.get(), modLoc("block/wisteria_door_bottom"), modLoc("block/wisteria_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.WISTERIA_TRAPDOOR.get(), modLoc("block/wisteria_trapdoor"), true, "cutout");

        blockItem(ModBlocks.WISTERIA_STAIRS);
        blockItem(ModBlocks.WISTERIA_SLAB);
        blockItem(ModBlocks.WISTERIA_PRESSURE_PLATE);
        blockItem(ModBlocks.WISTERIA_FENCE_GATE);
        blockItem(ModBlocks.WISTERIA_TRAPDOOR, "_bottom");

        // mush
        doubleFourPlaneCropBlock(ModBlocks.REEDS);

        // lupine
        twoPlanesCutoutMippedBlock(ModBlocks.LUPINE);

        // ashen
        blockWithItem(ModBlocks.VOLCANIC_ASH);

        // satis
        logBlock((ModBlocks.SATISTREE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.SATISTREE_WOOD.get()), blockTexture(ModBlocks.SATISTREE_LOG.get()), blockTexture(ModBlocks.SATISTREE_LOG.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_SATISTREE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_SATISTREE_WOOD.get()), blockTexture(ModBlocks.STRIPPED_SATISTREE_LOG.get()), blockTexture(ModBlocks.STRIPPED_SATISTREE_LOG.get()));

        blockItem(ModBlocks.SATISTREE_LOG);
        blockItem(ModBlocks.SATISTREE_WOOD);
        blockItem(ModBlocks.STRIPPED_SATISTREE_LOG);
        blockItem(ModBlocks.STRIPPED_SATISTREE_WOOD);

        blockWithItem(ModBlocks.SATISTREE_PLANKS);

        stairsBlock(ModBlocks.SATISTREE_STAIRS.get(), blockTexture(ModBlocks.SATISTREE_PLANKS.get()));
        slabBlock(ModBlocks.SATISTREE_SLAB.get(), blockTexture(ModBlocks.SATISTREE_PLANKS.get()), blockTexture(ModBlocks.SATISTREE_PLANKS.get()));

        buttonBlock(ModBlocks.SATISTREE_BUTTON.get(), blockTexture(ModBlocks.SATISTREE_PLANKS.get()));
        pressurePlateBlock(ModBlocks.SATISTREE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SATISTREE_PLANKS.get()));

        fenceBlock(ModBlocks.SATISTREE_FENCE.get(), blockTexture(ModBlocks.SATISTREE_PLANKS.get()));
        fenceGateBlock(ModBlocks.SATISTREE_FENCE_GATE.get(), blockTexture(ModBlocks.SATISTREE_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.SATISTREE_DOOR.get(), modLoc("block/satistree_door_bottom"), modLoc("block/satistree_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.SATISTREE_TRAPDOOR.get(), modLoc("block/satistree_trapdoor"), true, "cutout");

        blockItem(ModBlocks.SATISTREE_STAIRS);
        blockItem(ModBlocks.SATISTREE_SLAB);
        blockItem(ModBlocks.SATISTREE_PRESSURE_PLATE);
        blockItem(ModBlocks.SATISTREE_FENCE_GATE);
        blockItem(ModBlocks.SATISTREE_TRAPDOOR, "_bottom");

        twoPlanesCutoutBlock(ModBlocks.ALIEN_PHYLLOSTACHYS_SAPLING);
        // alien bamboo is made manually

        blockWithItem(ModBlocks.ALIEN_FENCE_PLANKS);
        fenceBlock(ModBlocks.ALIEN_FENCE_PLANT.get(), blockTexture(ModBlocks.ALIEN_FENCE_PLANKS.get()));

        // cresset flower manually
    }

    private void pottedPlantBlock(Supplier<FlowerPotBlock> pottedBlock, Supplier<Block> plantBlock) {
        String pottedName = Utils.findBlockId(pottedBlock);
        ResourceLocation plantTexture = blockTexture(plantBlock.get());

        ModelFile pottedModel = models()
                .withExistingParent(pottedName, mcLoc("block/flower_pot_cross"))
                .texture("plant", plantTexture)
                .renderType("cutout");

        simpleBlock(pottedBlock.get(), pottedModel);
    }

    private void thickPumpkinBlock(Supplier<Block> block) {
        Block b = block.get();
        String baseName = Utils.findBlockId(block);

        ResourceLocation side = modLoc("block/" + baseName + "_side");
        ResourceLocation end = modLoc("block/" + baseName + "_end");
        ResourceLocation inner = modLoc("block/thick_pumpkin_inner");

        var builder = getMultipartBuilder(b);

        for (Direction dir : Direction.values()) {
            ResourceLocation faceTex = (dir == Direction.UP || dir == Direction.DOWN) ? end : side;

            String dirName = dir.getName();

            var outerModel = models().withExistingParent(baseName + "_" + dirName, mcLoc("block/block"))
                    .element()
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(dir).texture("#texture").end()
                    .end()
                    .texture("texture", faceTex)
                    .texture("particle", faceTex);

            var innerModel = models().withExistingParent(baseName + "_" + dirName + "_inner", mcLoc("block/block"))
                    .element()
                    .from(0, 0, 0).to(16, 16, 16)
                    .face(dir).texture("#texture").end()
                    .end()
                    .texture("texture", inner)
                    .texture("particle", inner);

            BooleanProperty prop = switch (dir) {
                case NORTH -> BlockStateProperties.NORTH;
                case SOUTH -> BlockStateProperties.SOUTH;
                case EAST -> BlockStateProperties.EAST;
                case WEST -> BlockStateProperties.WEST;
                case UP -> BlockStateProperties.UP;
                case DOWN -> BlockStateProperties.DOWN;
            };

            builder
                    .part().modelFile(outerModel)
                    .addModel()
                    .condition(prop, Boolean.TRUE)
                    .end()
                    .part().modelFile(innerModel)
                    .addModel()
                    .condition(prop, Boolean.FALSE)
                    .end();
        }
    }

    private void thickPumpkinBlockItem(Supplier<Block> block) {
        String name = Utils.findBlockId(block);
        ResourceLocation side = modLoc("block/thick_pumpkin_side");
        ResourceLocation end = modLoc("block/thick_pumpkin_end");
        ResourceLocation inner = modLoc("block/thick_pumpkin_inner");

        models().withExistingParent(name, mcLoc("block/cube"))
                .texture("north", inner)
                .texture("south", side)
                .texture("east", side)
                .texture("west", inner)
                .texture("up", end)
                .texture("down", end);

        simpleBlockItem(block.get(), models().getExistingFile(modLoc(name)));
    }

    private void randomVariantTwoPlaneCutout(Supplier<Block> block, int variantCount) {
        String baseName = Utils.findBlockId(block);

        getVariantBuilder(block.get()).forAllStates(state -> {
            int variant = state.getValue(SpikyArchesBlock.VARIANT);
            return ConfiguredModel.builder()
                    .modelFile(models()
                            .cross(baseName + "_" + variant, modLoc("block/" + baseName + "_" + variant))
                            .renderType("cutout"))
                    .build();
        });

        for (int i = 0; i < variantCount; i++) {
            models().cross(baseName + "_" + i, modLoc("block/" + baseName + "_" + i));
        }
    }

    private void twoPlanesCutoutBlock(Supplier<Block> block) {
        simpleBlock(block.get(),
                models().cross(Utils.findBlockId(block), blockTexture(block.get())));
    }

    private void twoPlanesCutoutMippedBlock(Supplier<Block> block) {
        simpleBlock(block.get(),
                models().cross(Utils.findBlockId(block), blockTexture(block.get())).renderType("cutout_mipped"));
    }

    private void doubleFourPlaneCropBlock(Supplier<Block> block) {
        Block b = block.get();
        String name = Utils.findBlockId(block);

        models().withExistingParent(name + "_bottom", mcLoc("block/crop"))
                .texture("crop", modLoc("block/" + name + "_bottom"))
                .renderType("cutout_mipped");

        models().withExistingParent(name + "_top", mcLoc("block/crop"))
                .texture("crop", modLoc("block/" + name + "_top"))
                .renderType("cutout_mipped");

        getVariantBuilder(b).forAllStates(state -> {
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            String suffix = (half == DoubleBlockHalf.UPPER) ? "_top" : "_bottom";
            return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc(name + suffix))).build();
        });
    }

    private void leavesBlock(Supplier<Block> block) {
        simpleBlockWithItem(block.get(),
                models().singleTexture(Utils.findBlockId(block), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(block.get())));
    }

    private void iceBlock(Supplier<Block> block) {
        simpleBlockWithItem(block.get(),
                models().cubeAll(Utils.findBlockId(block), blockTexture(block.get())).renderType("translucent"));
    }

    private void blockWithItem(Supplier<? extends Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void blockItem(Supplier<? extends Block> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("amaranth:block/" + Utils.findBlockId(block)));
    }

    private void blockItem(Supplier<? extends Block> block, String appendix) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("amaranth:block/" + Utils.findBlockId(block) + appendix));
    }

    private static final Map<List<Supplier<Block>>, String> RENDER_TYPE_LISTS = Map.of(
            ModBlocks.MOD_CUTOUT_BLOCKS, "cutout",
            ModBlocks.MOD_CUTOUT_MIPPED_BLOCKS, "cutout_mipped",
            ModBlocks.MOD_TRANSLUCENT_BLOCKS, "translucent"
    );

    @Override
    public void simpleBlock(Block block, ModelFile model) {
        super.simpleBlock(block, applyRenderType(block, model));
    }

    /// render types are partially spliced in from the ModBlocks maps (which are also used for fabric)
    /// tho if using the builder directly those need to be added manually,
    /// and it's fine hardcoding the render type in this class too
    private ModelFile applyRenderType(Block block, ModelFile model) {
        return RENDER_TYPE_LISTS.entrySet().stream()
                .filter(e -> e.getKey().stream().filter(Objects::nonNull).anyMatch(s -> s.get() == block))
                .findFirst()
                .map(e -> (ModelFile) models().getBuilder(Utils.findBlockId(block))
                        .parent(getParent(model))
                        .renderType(e.getValue()))
                .orElse(model);
    }

    private static ModelFile getParent(ModelFile model) {
        try {
            Field parentField = ModelBuilder.class.getDeclaredField("parent");
            parentField.setAccessible(true);
            ModelFile parent = (ModelFile) parentField.get(model);
            return parent != null ? parent : model;
        } catch (ReflectiveOperationException e) {
            Constants.LOG.error("Amaranth: Failed to get parent of model of: {}", model);
            return model;
        }
    }
}
