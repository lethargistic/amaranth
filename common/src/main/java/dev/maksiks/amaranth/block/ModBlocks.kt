package dev.maksiks.amaranth.block;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.block.custom.*;
import dev.maksiks.amaranth.block.custom.leaves.*;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.platform.Services;
import dev.maksiks.amaranth.util.Utils;
import dev.maksiks.amaranth.worldgen.tree.ModTreeGrowers;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModBlocks {
    public static final HashMap<String, Supplier<? extends Block>> BLOCK_MAP = new HashMap<>();
    public static HashMap<Supplier<Block>, Supplier<FlowerPotBlock>> MOD_FLOWER_POTS = new HashMap<>();

    public record FlammabilityData(int burn, int spread) {
    }

    public static HashMap<Supplier<? extends Block>, FlammabilityData> FABRIC_MOD_FLAMMABLE_BLOCKS = new HashMap<>();
    public static Map<Supplier<? extends Block>, Supplier<Block>> MOD_STRIPPABLES = new HashMap<>();

    private static final Supplier<BlockBehaviour.Properties> normalWoodProps = () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F, 3.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava();

    // misc
    // mmm yes calcite 2
    public static final Supplier<Block> MARBLE = registerWithItem("marble",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)));

    // mystic
    public static final Supplier<Block> STRIPPED_MYSTIC_LOG = registerWithItem("stripped_mystic_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)));
    public static final Supplier<RotatedPillarBlock> MYSTIC_LOG = registerStrippablePillarBlock("mystic_log", STRIPPED_MYSTIC_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG));
    public static final Supplier<Block> STRIPPED_MYSTIC_WOOD = registerWithItem("stripped_mystic_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)));
    public static final Supplier<RotatedPillarBlock> MYSTIC_WOOD = registerStrippablePillarBlock("mystic_wood", STRIPPED_MYSTIC_WOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD));

    public static final Supplier<Block> MYSTIC_PLANKS = registerWithItem("mystic_planks",
            () -> new FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));

    public static final Supplier<Block> MYSTIC_LEAVES = registerWithItem("mystic_leaves",
            () -> new FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> MYSTIC_SAPLING = registerWithItem("mystic_sapling",
            () -> new SaplingBlock(ModTreeGrowers.MYSTIC_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_MYSTIC_SAPLING = registerFlowerPot(MYSTIC_SAPLING);

    // non-full block stuff
    public static final Supplier<StairBlock> MYSTIC_STAIRS = registerWithItem("mystic_stairs",
            () -> new StairBlock(ModBlocks.MYSTIC_PLANKS.get().defaultBlockState(), normalWoodProps.get()));
    public static final Supplier<SlabBlock> MYSTIC_SLAB = registerWithItem("mystic_slab",
            () -> new SlabBlock(normalWoodProps.get()));

    public static final Supplier<PressurePlateBlock> MYSTIC_PRESSURE_PLATE = registerWithItem("mystic_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()));
    public static final Supplier<ButtonBlock> MYSTIC_BUTTON = registerWithItem("mystic_button",
            () -> new ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get().noCollission()));

    public static final Supplier<FenceBlock> MYSTIC_FENCE = registerWithItem("mystic_fence",
            () -> new FenceBlock(normalWoodProps.get()));
    public static final Supplier<FenceGateBlock> MYSTIC_FENCE_GATE = registerWithItem("mystic_fence_gate",
            () -> new FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()));

    // TODO fabrec: no occlusion?
    public static final Supplier<DoorBlock> MYSTIC_DOOR = registerWithItem("mystic_door",
            () -> new DoorBlock(BlockSetType.SPRUCE, BlockBehaviour.Properties.of().strength(2F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final Supplier<TrapDoorBlock> MYSTIC_TRAPDOOR = registerWithItem("mystic_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.SPRUCE, normalWoodProps.get().noOcclusion().isValidSpawn(Blocks::never)));

    // sign
    // hanged sign
    // boat
    // chest boat

    // stubby
    public static final Supplier<Block> STUBBY_SAPLING = registerWithItem("stubby_sapling",
            () -> new SaplingBlock(ModTreeGrowers.STUBBY_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_STUBBY_SAPLING = registerFlowerPot(STUBBY_SAPLING);

    // silver birch
    public static final Supplier<Block> SILVERY_SILVER_BIRCH_LEAVES = registerWithItem("silvery_silver_birch_leaves",
            () -> new SilverBirchLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> LIGHT_SILVER_BIRCH_LEAVES = registerWithItem("light_silver_birch_leaves",
            () -> new SilverBirchLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> DARK_SILVER_BIRCH_LEAVES = registerWithItem("dark_silver_birch_leaves",
            () -> new SilverBirchLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> SILVER_BIRCH_SAPLING = registerWithItem("silver_birch_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SILVER_BIRCH_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_SILVER_BIRCH_SAPLING = registerFlowerPot(SILVER_BIRCH_SAPLING);

    public static final Supplier<Block> GOLDEN_LEAF_LITTER = registerWithItem("golden_leaf_litter",
            () -> new GoldenLeafLitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS)));

    // desolate ice fields
    public static final Supplier<Block> SORROW_ICE = registerWithItem("sorrow_ice",
            () -> new IceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE)));
    public static final Supplier<Block> REMNANT_SORROW_ICE = registerWithItem("remnant_sorrow_ice",
            () -> new HalfTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE)));

    // mixed forest
    public static final Supplier<Block> PURPLE_MIXED_OAK_LEAVES = registerWithItem("purple_mixed_oak_leaves",
            () -> new FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final Supplier<Block> YELLOW_MIXED_OAK_LEAVES = registerWithItem("yellow_mixed_oak_leaves",
            () -> new FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final Supplier<Block> RED_MIXED_OAK_LEAVES = registerWithItem("red_mixed_oak_leaves",
            () -> new FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final Supplier<Block> PURPLE_MIXED_OAK_SAPLING = registerWithItem("purple_mixed_oak_sapling",
            () -> new SaplingBlock(ModTreeGrowers.PURPLE_MIXED_OAK_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_PURPLE_MIXED_OAK_SAPLING = registerFlowerPot(PURPLE_MIXED_OAK_SAPLING);

    public static final Supplier<Block> RED_MIXED_OAK_SAPLING = registerWithItem("red_mixed_oak_sapling",
            () -> new SaplingBlock(ModTreeGrowers.RED_MIXED_OAK_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_RED_MIXED_OAK_SAPLING = registerFlowerPot(RED_MIXED_OAK_SAPLING);

    public static final Supplier<Block> YELLOW_MIXED_OAK_SAPLING = registerWithItem("yellow_mixed_oak_sapling",
            () -> new SaplingBlock(ModTreeGrowers.YELLOW_MIXED_OAK_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_YELLOW_MIXED_OAK_SAPLING = registerFlowerPot(YELLOW_MIXED_OAK_SAPLING);

    // orderly courts
    public static final Supplier<Block> TRIMMED_TREE_SAPLING = registerWithItem("trimmed_tree_sapling",
            () -> new SaplingBlock(ModTreeGrowers.TRIMMED_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_TRIMMED_TREE_SAPLING = registerFlowerPot(TRIMMED_TREE_SAPLING);

    // anthocyanin
    public static final Supplier<Block> STRIPPED_ANTHOCYANIN_LOG = registerWithItem("stripped_anthocyanin_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)));
    public static final Supplier<RotatedPillarBlock> ANTHOCYANIN_LOG = registerStrippablePillarBlock("anthocyanin_log", STRIPPED_ANTHOCYANIN_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG));
    public static final Supplier<Block> STRIPPED_ANTHOCYANIN_WOOD = registerWithItem("stripped_anthocyanin_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)));
    public static final Supplier<RotatedPillarBlock> ANTHOCYANIN_WOOD = registerStrippablePillarBlock("anthocyanin_wood", STRIPPED_ANTHOCYANIN_WOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD));

    public static final Supplier<Block> ANTHOCYANIN_PLANKS = registerWithItem("anthocyanin_planks",
            () -> new FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));

    public static final Supplier<Block> ANTHOCYANIN_LEAVES = registerWithItem("anthocyanin_leaves",
            () -> new AnthocyaninLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));
    public static final Supplier<Block> BLOOMING_ANTHOCYANIN_LEAVES = registerWithItem("blooming_anthocyanin_leaves",
            () -> new AnthocyaninLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> ANTHOCYANIN_SAPLING = registerWithItem("anthocyanin_sapling",
            () -> new SaplingBlock(ModTreeGrowers.ANTHOCYANIN_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_ANTHOCYANIN_SAPLING = registerFlowerPot(ANTHOCYANIN_SAPLING);

    // non-full block stuff
    public static final Supplier<StairBlock> ANTHOCYANIN_STAIRS = registerWithItem("anthocyanin_stairs",
            () -> new StairBlock(ModBlocks.ANTHOCYANIN_PLANKS.get().defaultBlockState(), normalWoodProps.get()));
    public static final Supplier<SlabBlock> ANTHOCYANIN_SLAB = registerWithItem("anthocyanin_slab",
            () -> new SlabBlock(normalWoodProps.get()));

    public static final Supplier<PressurePlateBlock> ANTHOCYANIN_PRESSURE_PLATE = registerWithItem("anthocyanin_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()));
    public static final Supplier<ButtonBlock> ANTHOCYANIN_BUTTON = registerWithItem("anthocyanin_button",
            () -> new ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get().noCollission()));

    public static final Supplier<FenceBlock> ANTHOCYANIN_FENCE = registerWithItem("anthocyanin_fence",
            () -> new FenceBlock(normalWoodProps.get()));
    public static final Supplier<FenceGateBlock> ANTHOCYANIN_FENCE_GATE = registerWithItem("anthocyanin_fence_gate",
            () -> new FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()));

    public static final Supplier<DoorBlock> ANTHOCYANIN_DOOR = registerWithItem("anthocyanin_door",
            () -> new DoorBlock(BlockSetType.SPRUCE, BlockBehaviour.Properties.of().strength(2F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final Supplier<DoorBlock> ORNAMENTED_ANTHOCYANIN_DOOR = registerWithItem("ornamented_anthocyanin_door",
            () -> new DoorBlock(BlockSetType.SPRUCE, BlockBehaviour.Properties.of().strength(2F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final Supplier<TrapDoorBlock> ANTHOCYANIN_TRAPDOOR = registerWithItem("anthocyanin_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.SPRUCE, normalWoodProps.get().noOcclusion().isValidSpawn(Blocks::never)));
    public static final Supplier<TrapDoorBlock> ORNAMENTED_ANTHOCYANIN_TRAPDOOR = registerWithItem("ornamented_anthocyanin_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.SPRUCE, normalWoodProps.get().noOcclusion().isValidSpawn(Blocks::never)));

    public static final Supplier<Block> MALACHITE_VIPERS_BUGLOSS = registerWithItem("malachite_vipers_bugloss",
            () -> new FlowerBlock(MobEffects.POISON, 0.35F, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public static final Supplier<FlowerPotBlock> POTTED_MALACHITE_VIPERS_BUGLOSS = registerFlowerPot(MALACHITE_VIPERS_BUGLOSS);

    // pain
    public static final Supplier<Block> SPIKY_ARCHES = registerWithItem("spiky_arches",
            () -> new SpikyArchesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).forceSolidOn().strength(4.0F)));


    // thrumletons
    public static final Supplier<Block> THICK_PUMPKIN = registerWithItem("thick_pumpkin",
            () -> new ThickPumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN)));

    // speary
    public static final Supplier<Block> SPEARY_SAPLING = registerWithItem("speary_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SPEARY_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_SPEARY_SAPLING = registerFlowerPot(SPEARY_SAPLING);

    // pastel
public static final Supplier<Block> STRIPPED_WISTERIA_LOG = registerWithItem("stripped_wisteria_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)));
    public static final Supplier<Block> STRIPPED_WISTERIA_WOOD = registerWithItem("stripped_wisteria_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)));
    public static final Supplier<RotatedPillarBlock> JUICY_WISTERIA_LOG = registerStrippablePillarBlock("juicy_wisteria_log", STRIPPED_WISTERIA_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG));
    public static final Supplier<RotatedPillarBlock> WISTERIA_LOG = registerStrippablePillarBlock("wisteria_log", STRIPPED_WISTERIA_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG));
    public static final Supplier<RotatedPillarBlock> WISTERIA_WOOD = registerStrippablePillarBlock("wisteria_wood", STRIPPED_WISTERIA_WOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD));

    public static final Supplier<Block> WISTERIA_PLANKS = registerWithItem("wisteria_planks",
            () -> new FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));

    public static final Supplier<Block> WISTERIA_LEAVES = registerWithItem("wisteria_leaves",
            () -> new WisteriaLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> WISTERIA_SAPLING = registerWithItem("wisteria_sapling",
            () -> new SaplingBlock(ModTreeGrowers.WISTERIA_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_WISTERIA_SAPLING = registerFlowerPot(WISTERIA_SAPLING);

    // non-full block stuff
    public static final Supplier<StairBlock> WISTERIA_STAIRS = registerWithItem("wisteria_stairs",
            () -> new StairBlock(ModBlocks.WISTERIA_PLANKS.get().defaultBlockState(), normalWoodProps.get()));
    public static final Supplier<SlabBlock> WISTERIA_SLAB = registerWithItem("wisteria_slab",
            () -> new SlabBlock(normalWoodProps.get()));

    public static final Supplier<PressurePlateBlock> WISTERIA_PRESSURE_PLATE = registerWithItem("wisteria_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()));
    public static final Supplier<ButtonBlock> WISTERIA_BUTTON = registerWithItem("wisteria_button",
            () -> new ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get().noCollission()));

    public static final Supplier<FenceBlock> WISTERIA_FENCE = registerWithItem("wisteria_fence",
            () -> new FenceBlock(normalWoodProps.get()));
    public static final Supplier<FenceGateBlock> WISTERIA_FENCE_GATE = registerWithItem("wisteria_fence_gate",
            () -> new FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()));

    public static final Supplier<DoorBlock> WISTERIA_DOOR = registerWithItem("wisteria_door",
            () -> new DoorBlock(BlockSetType.SPRUCE, BlockBehaviour.Properties.of().strength(2F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final Supplier<TrapDoorBlock> WISTERIA_TRAPDOOR = registerWithItem("wisteria_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.SPRUCE, normalWoodProps.get().noOcclusion().isValidSpawn(Blocks::never)));

    public static final Supplier<Block> PHLOX = registerWithItem("phlox",
            () -> new PinkPetalsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS)));

    // mush
    public static final Supplier<Block> REEDS = registerWithItem("reeds",
            () -> new ReedsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)));

    public static final Supplier<Block> RED_MINI_SHROOM_SPORELING = registerWithItem("red_mini_shroom_sporeling",
            () -> new SaplingBlock(ModTreeGrowers.RED_MINI_SHROOM_GROWER,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.MUD)
                            .pushReaction(PushReaction.DESTROY)));
    public static final Supplier<FlowerPotBlock> POTTED_RED_MINI_SHROOM_SPORELING = registerFlowerPot(RED_MINI_SHROOM_SPORELING);

    public static final Supplier<Block> BROWN_MINI_SHROOM_SPORELING = registerWithItem("brown_mini_shroom_sporeling",
            () -> new SaplingBlock(ModTreeGrowers.BROWN_MINI_SHROOM_GROWER,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.MUD)
                            .pushReaction(PushReaction.DESTROY)));
    public static final Supplier<FlowerPotBlock> POTTED_BROWN_MINI_SHROOM_SPORELING = registerFlowerPot(BROWN_MINI_SHROOM_SPORELING);

    // witchy
    public static final Supplier<Block> WITCHY_SAPLING = registerWithItem("witchy_sapling",
            () -> new SaplingBlock(ModTreeGrowers.WITCHY_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_WITCHY_SAPLING = registerFlowerPot(WITCHY_SAPLING);

    // lupine
    public static final Supplier<Block> LUPINE = registerWithItem("lupine",
            () -> new FlowerBlockNonShifting(MobEffects.POISON, 0.35F, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.NONE)
                    .pushReaction(PushReaction.DESTROY)));
    public static final Supplier<FlowerPotBlock> POTTED_LUPINE = registerFlowerPot(LUPINE);

    // alpine
    public static final Supplier<Block> ALPINE_SPRUCE_SAPLING = registerWithItem("alpine_spruce_sapling",
            () -> new SaplingBlock(ModTreeGrowers.ALPINE_SPRUCE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_ALPINE_SPRUCE_SAPLING = registerFlowerPot(ALPINE_SPRUCE_SAPLING);

    // ashen
    public static final Supplier<Block> VOLCANIC_ASH = registerWithItem("volcanic_ash",
            () -> new ColoredFallingBlock(
                    new ColorRGBA(2170911),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)));

    // satis
    public static final Supplier<Block> STRIPPED_SATISTREE_LOG = registerWithItem("stripped_satistree_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)));
    public static final Supplier<RotatedPillarBlock> SATISTREE_LOG = registerStrippablePillarBlock("satistree_log",
            STRIPPED_SATISTREE_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG));
    public static final Supplier<Block> SATISTREE_WOOD = registerWithItem("satistree_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD)));
    public static final Supplier<Block> STRIPPED_SATISTREE_WOOD = registerWithItem("stripped_satistree_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)));

    public static final Supplier<Block> SATISTREE_PLANKS = registerWithItem("satistree_planks",
            () -> new FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));

    public static final Supplier<Block> ALIEN_LEAVES = registerWithItem("alien_leaves",
            () -> new AlienLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));

    public static final Supplier<Block> SATISTREE_SAPLING = registerWithItem("satistree_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SATISTREE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_SATISTREE_SAPLING = registerFlowerPot(SATISTREE_SAPLING);
    public static final Supplier<Block> GIGANTIC_SATISTREE_SPROUTS = registerWithItem("gigantic_satistree_sprouts",
            () -> new SaplingBlock(ModTreeGrowers.GIGANTIC_SATISTREE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));

    // non-full block stuff
    public static final Supplier<StairBlock> SATISTREE_STAIRS = registerWithItem("satistree_stairs",
            () -> new StairBlock(ModBlocks.SATISTREE_PLANKS.get().defaultBlockState(), normalWoodProps.get()));
    public static final Supplier<SlabBlock> SATISTREE_SLAB = registerWithItem("satistree_slab",
            () -> new SlabBlock(normalWoodProps.get()));

    public static final Supplier<PressurePlateBlock> SATISTREE_PRESSURE_PLATE = registerWithItem("satistree_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()));
    public static final Supplier<ButtonBlock> SATISTREE_BUTTON = registerWithItem("satistree_button",
            () -> new ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get().noCollission()));

    public static final Supplier<FenceBlock> SATISTREE_FENCE = registerWithItem("satistree_fence",
            () -> new FenceBlock(normalWoodProps.get()));
    public static final Supplier<FenceGateBlock> SATISTREE_FENCE_GATE = registerWithItem("satistree_fence_gate",
            () -> new FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()));

    public static final Supplier<DoorBlock> SATISTREE_DOOR = registerWithItem("satistree_door",
            () -> new DoorBlock(BlockSetType.SPRUCE, BlockBehaviour.Properties.of().strength(2F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final Supplier<TrapDoorBlock> SATISTREE_TRAPDOOR = registerWithItem("satistree_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.SPRUCE, normalWoodProps.get().noOcclusion().isValidSpawn(Blocks::never)));

    public static final Supplier<Block> ALIEN_PHYLLOSTACHYS_SAPLING = registerWithItem("alien_phyllostachys_sapling",
            () -> new AlienPhyllostachysSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING)));
    public static final Supplier<Block> ALIEN_PHYLLOSTACHYS = registerWithItem("alien_phyllostachys",
            () -> new AlienPhyllostachysStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO)));
    public static final Supplier<FlowerPotBlock> POTTED_ALIEN_PHYLLOSTACHYS = registerFlowerPot(ALIEN_PHYLLOSTACHYS);
    public static final Supplier<Block> ALIEN_FENCE_PLANKS = registerWithItem("alien_fence_planks",
            () -> new FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    // chewy on the inside!
    public static final Supplier<FenceBlock> ALIEN_FENCE_PLANT = registerWithItem("alien_fence_plant",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BANJO)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WET_GRASS)
                    .ignitedByLava()));

    public static final Supplier<Block> ALIEN_FENCE_PLANT_SAPLING = registerWithItem("alien_fence_plant_sapling",
            () -> new SaplingBlock(ModTreeGrowers.ALIEN_FENCE_PLANT_GROWER,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.WET_GRASS)
                            .pushReaction(PushReaction.DESTROY)));
    public static final Supplier<FlowerPotBlock> POTTED_ALIEN_FENCE_PLANT_SAPLING = registerFlowerPot(ALIEN_FENCE_PLANT_SAPLING);

    // shrub
    public static final Supplier<Block> SHRUB_SAPLING = registerWithItem("shrub_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SHRUB_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));
    public static final Supplier<FlowerPotBlock> POTTED_SHRUB_SAPLING = registerFlowerPot(SHRUB_SAPLING);

    private static Supplier<RotatedPillarBlock> registerStrippablePillarBlock(String name, Supplier<Block> stripped, BlockBehaviour.Properties props) {
        Supplier<RotatedPillarBlock> memoized = Suppliers.memoize(Services.PLATFORM.createLoaderStrippableLog(props));
        MOD_STRIPPABLES.put(memoized, stripped);
        registerWithItem(name, memoized);
        return memoized;
    }

    // friendly reminder to do this with Neo's methods too!
    public static <B extends Block> void registerFabricFlammability(Supplier<B> block, int burn, int spread) {
        FABRIC_MOD_FLAMMABLE_BLOCKS.put(block, new FlammabilityData(burn, spread));
    }

    private static Supplier<FlowerPotBlock> registerFlowerPot(Supplier<Block> plant) {
        String plantName = Utils.findBlockIdByMap(plant);
        Supplier<FlowerPotBlock> pot = register("potted_" + plantName,
                Suppliers.memoize(() -> new FlowerPotBlock(plant.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_SPRUCE_SAPLING))));
        MOD_FLOWER_POTS.put(plant, pot);
        return pot;
    }

    public static <B extends Block> Supplier<B> registerWithItem(String key, Supplier<B> block) {
        Supplier<B> memoized = Suppliers.memoize(block);
        BLOCK_MAP.put(key, memoized);
        ModItems.register(key, () -> new BlockItem(memoized.get(), new Item.Properties()));
        return memoized;
    }

    public static <B extends Block> Supplier<B> register(String key, Supplier<B> block) {
        Supplier<B> memoized = Suppliers.memoize(block);
        BLOCK_MAP.put(key, memoized);
        return memoized;
    }
}
