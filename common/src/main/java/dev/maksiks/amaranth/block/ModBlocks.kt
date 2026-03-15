package dev.maksiks.amaranth.block

import com.google.common.base.Supplier
import com.google.common.base.Suppliers
import dev.maksiks.amaranth.block.custom.*
import dev.maksiks.amaranth.block.custom.leaves.*
import dev.maksiks.amaranth.item.ModItems
import dev.maksiks.amaranth.platform.Services
import dev.maksiks.amaranth.util.Utils
import dev.maksiks.amaranth.worldgen.tree.ModTreeGrowers
import net.minecraft.core.BlockPos
import net.minecraft.util.ColorRGBA
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

class ModBlocks {
    companion object {
        enum class RenderType {
            CUTOUT, CUTOUT_MIPPED, TRANSLUCENT, NONE
        }

        // functional stores
        @JvmField
        val BLOCK_MAP: HashMap<String?, Supplier<out Block?>?> = HashMap<String?, Supplier<out Block?>?>()

        @JvmField
        var MOD_FLOWER_POTS: HashMap<Supplier<Block?>?, Supplier<FlowerPotBlock?>?> =
            HashMap<Supplier<Block?>?, Supplier<FlowerPotBlock?>?>()

        @JvmField
        var MOD_CUTOUT_BLOCKS: MutableList<Supplier<Block?>?> = ArrayList<Supplier<Block?>?>()

        @JvmField
        var MOD_CUTOUT_MIPPED_BLOCKS: MutableList<Supplier<Block?>?> = ArrayList<Supplier<Block?>?>()

        @JvmField
        var MOD_TRANSLUCENT_BLOCKS: MutableList<Supplier<Block?>?> = ArrayList<Supplier<Block?>?>()

        @JvmField
        var FABRIC_MOD_FLAMMABLE_BLOCKS: HashMap<Supplier<out Block?>?, FlammabilityData?> =
            HashMap<Supplier<out Block?>?, FlammabilityData?>()

        @JvmField
        var MOD_STRIPPABLES: MutableMap<Supplier<out Block?>?, Supplier<Block?>?> =
            HashMap<Supplier<out Block?>?, Supplier<Block?>?>()

        // group stores
        @JvmField
        var MOD_LEAVES: MutableList<Supplier<Block?>?> = ArrayList<Supplier<Block?>?>()


        private val normalWoodProps = Supplier {
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0f, 3.0f)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        }

        fun doorBasedOnSpruce(): DoorBlock = DoorBlock(
            BlockSetType.SPRUCE,
            BlockBehaviour.Properties.of().strength(2f).noOcclusion()
                .isValidSpawn { state: BlockState?, blockGetter: BlockGetter?, pos: BlockPos?, entity: EntityType<*>? ->
                    Blocks.never(
                        state!!,
                        blockGetter!!,
                        pos!!,
                        entity!!
                    )
                }
        )

        fun trapdoorFromSpruce(): TrapDoorBlock = TrapDoorBlock(
            BlockSetType.SPRUCE,
            normalWoodProps.get()!!.noOcclusion()
                .isValidSpawn { state: BlockState?, blockGetter: BlockGetter?, pos: BlockPos?, entity: EntityType<*>? ->
                    Blocks.never(
                        state!!,
                        blockGetter!!,
                        pos!!,
                        entity!!
                    )
                }
        )

        // misc
        // mmm yes calcite 2
        @JvmField
        val MARBLE: Supplier<Block?> = registerWithItem<Block?>(
            "marble"
        ) { Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)) }

        // mystic
        @JvmField
        val STRIPPED_MYSTIC_LOG: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_mystic_log"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)) }

        @JvmField
        val MYSTIC_LOG: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "mystic_log", STRIPPED_MYSTIC_LOG, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_LOG
            )
        )

        @JvmField
        val STRIPPED_MYSTIC_WOOD: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_mystic_wood"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)) }

        @JvmField
        val MYSTIC_WOOD: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "mystic_wood", STRIPPED_MYSTIC_WOOD, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_WOOD
            )
        )

        @JvmField
        val MYSTIC_PLANKS: Supplier<Block?> = registerWithItem<Block?>(
            "mystic_planks"
        ) { FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)) }

        @JvmField
        val MYSTIC_LEAVES: Supplier<Block?> = registerLeaves("mystic_leaves")

        @JvmField
        val MYSTIC_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "mystic_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.MYSTIC_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_MYSTIC_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(MYSTIC_SAPLING)

        // non-full block stuff
        @JvmField
        val MYSTIC_STAIRS: Supplier<StairBlock?> = registerWithItem<StairBlock?>(
            "mystic_stairs"
        ) { StairBlock(MYSTIC_PLANKS.get()!!.defaultBlockState(), normalWoodProps.get()!!) }

        @JvmField
        val MYSTIC_SLAB: Supplier<SlabBlock?> = registerWithItem<SlabBlock?>(
            "mystic_slab"
        ) { SlabBlock(normalWoodProps.get()!!) }

        @JvmField
        val MYSTIC_PRESSURE_PLATE: Supplier<PressurePlateBlock?> = registerWithItem<PressurePlateBlock?>(
            "mystic_pressure_plate"
        ) { PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val MYSTIC_BUTTON: Supplier<ButtonBlock?> = registerWithItem<ButtonBlock?>(
            "mystic_button"
        ) { ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get()!!.noCollission()) }

        @JvmField
        val MYSTIC_FENCE: Supplier<FenceBlock?> = registerWithItem<FenceBlock?>(
            "mystic_fence"
        ) { FenceBlock(normalWoodProps.get()!!) }

        @JvmField
        val MYSTIC_FENCE_GATE: Supplier<FenceGateBlock?> = registerWithItem<FenceGateBlock?>(
            "mystic_fence_gate"
        ) { FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()!!) }

        // TODO fabrec: no occlusion?
        @JvmField
        val MYSTIC_DOOR: Supplier<DoorBlock?> = registerWithItem<DoorBlock?>(
            "mystic_door", renderType = RenderType.CUTOUT
        ) {
            doorBasedOnSpruce()
        }

        @JvmField
        val MYSTIC_TRAPDOOR: Supplier<TrapDoorBlock?> = registerWithItem<TrapDoorBlock?>(
            "mystic_trapdoor", renderType = RenderType.CUTOUT
        ) {
            trapdoorFromSpruce()
        }

        // sign
        // hanged sign
        // boat
        // chest boat
        // stubby
        @JvmField
        val STUBBY_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "stubby_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.STUBBY_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_STUBBY_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(STUBBY_SAPLING)

        // silver birch
        @JvmField
        val SILVERY_SILVER_BIRCH_LEAVES: Supplier<Block?> = registerLeaves("silvery_silver_birch_leaves",
            constructor = ::SilverBirchLeavesBlock)

        @JvmField
        val LIGHT_SILVER_BIRCH_LEAVES: Supplier<Block?> = registerLeaves("light_silver_birch_leaves",
            constructor = ::SilverBirchLeavesBlock)

        @JvmField
        val DARK_SILVER_BIRCH_LEAVES: Supplier<Block?> = registerLeaves("dark_silver_birch_leaves",
            constructor = ::SilverBirchLeavesBlock)

        @JvmField
        val SILVER_BIRCH_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "silver_birch_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.SILVER_BIRCH_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_SILVER_BIRCH_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(SILVER_BIRCH_SAPLING)

        @JvmField
        val GOLDEN_LEAF_LITTER: Supplier<Block?> = registerWithItem<Block?>(
            "golden_leaf_litter", renderType = RenderType.CUTOUT
        ) { GoldenLeafLitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS)) }

        // desolate ice fields
        @JvmField
        val SORROW_ICE: Supplier<Block?> = registerWithItem<Block?>(
            "sorrow_ice", renderType = RenderType.TRANSLUCENT,
        ) { IceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE)) }

        @JvmField
        val REMNANT_SORROW_ICE: Supplier<Block?> = registerWithItem<Block?>(
            "remnant_sorrow_ice"
        ) { HalfTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE)) }

        // mixed forest
        @JvmField
        val PURPLE_MIXED_OAK_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "purple_mixed_oak_leaves"
        ) { FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) }
        val PURPLE_MIXED_OAK_LEAVES: Supplier<Block?> = registerLeaves("purple_mixed_oak_leaves")

        @JvmField
        val YELLOW_MIXED_OAK_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "yellow_mixed_oak_leaves"
        ) { FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) }

        @JvmField
        val RED_MIXED_OAK_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "red_mixed_oak_leaves"
        ) { FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) }

        @JvmField
        val PURPLE_MIXED_OAK_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "purple_mixed_oak_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.PURPLE_MIXED_OAK_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
            )
        }

        @JvmField
        val POTTED_PURPLE_MIXED_OAK_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(PURPLE_MIXED_OAK_SAPLING)

        @JvmField
        val RED_MIXED_OAK_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "red_mixed_oak_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.RED_MIXED_OAK_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
            )
        }

        @JvmField
        val POTTED_RED_MIXED_OAK_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(RED_MIXED_OAK_SAPLING)

        @JvmField
        val YELLOW_MIXED_OAK_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "yellow_mixed_oak_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.YELLOW_MIXED_OAK_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
            )
        }

        @JvmField
        val POTTED_YELLOW_MIXED_OAK_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(YELLOW_MIXED_OAK_SAPLING)

        // orderly courts
        @JvmField
        val TRIMMED_TREE_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "trimmed_tree_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.TRIMMED_TREE_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
            )
        }

        @JvmField
        val POTTED_TRIMMED_TREE_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(TRIMMED_TREE_SAPLING)

        // anthocyanin
        @JvmField
        val STRIPPED_ANTHOCYANIN_LOG: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_anthocyanin_log"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)) }

        @JvmField
        val ANTHOCYANIN_LOG: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "anthocyanin_log", STRIPPED_ANTHOCYANIN_LOG, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_LOG
            )
        )

        @JvmField
        val STRIPPED_ANTHOCYANIN_WOOD: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_anthocyanin_wood"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)) }

        @JvmField
        val ANTHOCYANIN_WOOD: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "anthocyanin_wood", STRIPPED_ANTHOCYANIN_WOOD, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_WOOD
            )
        )

        @JvmField
        val ANTHOCYANIN_PLANKS: Supplier<Block?> = registerWithItem<Block?>(
            "anthocyanin_planks"
        ) { FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)) }

        @JvmField
        val ANTHOCYANIN_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "anthocyanin_leaves"
        ) { AnthocyaninLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)) }

        @JvmField
        val BLOOMING_ANTHOCYANIN_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "blooming_anthocyanin_leaves"
        ) { AnthocyaninLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)) }

        @JvmField
        val ANTHOCYANIN_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "anthocyanin_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.ANTHOCYANIN_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_ANTHOCYANIN_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(ANTHOCYANIN_SAPLING)

        // non-full block stuff
        @JvmField
        val ANTHOCYANIN_STAIRS: Supplier<StairBlock?> = registerWithItem<StairBlock?>(
            "anthocyanin_stairs"
        ) { StairBlock(ANTHOCYANIN_PLANKS.get()!!.defaultBlockState(), normalWoodProps.get()!!) }

        @JvmField
        val ANTHOCYANIN_SLAB: Supplier<SlabBlock?> = registerWithItem<SlabBlock?>(
            "anthocyanin_slab"
        ) { SlabBlock(normalWoodProps.get()!!) }

        @JvmField
        val ANTHOCYANIN_PRESSURE_PLATE: Supplier<PressurePlateBlock?> = registerWithItem<PressurePlateBlock?>(
            "anthocyanin_pressure_plate"
        ) { PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val ANTHOCYANIN_BUTTON: Supplier<ButtonBlock?> = registerWithItem<ButtonBlock?>(
            "anthocyanin_button"
        ) { ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get()!!.noCollission()) }

        @JvmField
        val ANTHOCYANIN_FENCE: Supplier<FenceBlock?> = registerWithItem<FenceBlock?>(
            "anthocyanin_fence"
        ) { FenceBlock(normalWoodProps.get()!!) }

        @JvmField
        val ANTHOCYANIN_FENCE_GATE: Supplier<FenceGateBlock?> = registerWithItem<FenceGateBlock?>(
            "anthocyanin_fence_gate"
        ) { FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val ANTHOCYANIN_DOOR: Supplier<DoorBlock?> = registerWithItem<DoorBlock?>(
            "anthocyanin_door", renderType = RenderType.CUTOUT
        ) {
            doorBasedOnSpruce()
        }

        @JvmField
        val ORNAMENTED_ANTHOCYANIN_DOOR: Supplier<DoorBlock?> = registerWithItem<DoorBlock?>(
            "ornamented_anthocyanin_door", renderType = RenderType.CUTOUT
        ) {
            doorBasedOnSpruce()
        }

        @JvmField
        val ANTHOCYANIN_TRAPDOOR: Supplier<TrapDoorBlock?> = registerWithItem<TrapDoorBlock?>(
            "anthocyanin_trapdoor", renderType = RenderType.CUTOUT
        ) {
            trapdoorFromSpruce()
        }

        @JvmField
        val ORNAMENTED_ANTHOCYANIN_TRAPDOOR: Supplier<TrapDoorBlock?> = registerWithItem<TrapDoorBlock?>(
            "ornamented_anthocyanin_trapdoor", renderType = RenderType.CUTOUT
        ) {
            trapdoorFromSpruce()
        }

        @JvmField
        val MALACHITE_VIPERS_BUGLOSS: Supplier<Block?> = registerWithItem<Block?>(
            "malachite_vipers_bugloss", renderType = RenderType.CUTOUT
        ) { FlowerBlock(MobEffects.POISON, 0.35f, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)) }

        @JvmField
        val POTTED_MALACHITE_VIPERS_BUGLOSS: Supplier<FlowerPotBlock?> = registerFlowerPot(MALACHITE_VIPERS_BUGLOSS)

        // pain
        @JvmField
        val SPIKY_ARCHES: Supplier<Block?> = registerWithItem<Block?>(
            "spiky_arches", renderType = RenderType.CUTOUT
        ) {
            SpikyArchesBlock(
                BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).forceSolidOn().strength(4.0f)
            )
        }


        // thrumletons
        @JvmField
        val THICK_PUMPKIN: Supplier<Block?> = registerWithItem<Block?>(
            "thick_pumpkin"
        ) { ThickPumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN)) }

        // speary
        @JvmField
        val SPEARY_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "speary_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.SPEARY_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_SPEARY_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(SPEARY_SAPLING)

        // pastel
        @JvmField
        val STRIPPED_WISTERIA_LOG: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_wisteria_log"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)) }

        @JvmField
        val STRIPPED_WISTERIA_WOOD: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_wisteria_wood"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)) }

        @JvmField
        val JUICY_WISTERIA_LOG: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "juicy_wisteria_log", STRIPPED_WISTERIA_LOG, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_LOG
            )
        )

        @JvmField
        val WISTERIA_LOG: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "wisteria_log", STRIPPED_WISTERIA_LOG, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_LOG
            )
        )

        @JvmField
        val WISTERIA_WOOD: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "wisteria_wood", STRIPPED_WISTERIA_WOOD, BlockBehaviour.Properties.ofFullCopy(
                Blocks.SPRUCE_WOOD
            )
        )

        @JvmField
        val WISTERIA_PLANKS: Supplier<Block?> = registerWithItem<Block?>(
            "wisteria_planks"
        ) { FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)) }

        @JvmField
        val WISTERIA_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "wisteria_leaves", renderType = RenderType.CUTOUT
        ) { WisteriaLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)) }

        @JvmField
        val WISTERIA_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "wisteria_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.WISTERIA_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_WISTERIA_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(WISTERIA_SAPLING)

        // non-full block stuff
        @JvmField
        val WISTERIA_STAIRS: Supplier<StairBlock?> = registerWithItem<StairBlock?>(
            "wisteria_stairs"
        ) { StairBlock(WISTERIA_PLANKS.get()!!.defaultBlockState(), normalWoodProps.get()!!) }

        @JvmField
        val WISTERIA_SLAB: Supplier<SlabBlock?> = registerWithItem<SlabBlock?>(
            "wisteria_slab"
        ) { SlabBlock(normalWoodProps.get()!!) }

        @JvmField
        val WISTERIA_PRESSURE_PLATE: Supplier<PressurePlateBlock?> = registerWithItem<PressurePlateBlock?>(
            "wisteria_pressure_plate"
        ) { PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val WISTERIA_BUTTON: Supplier<ButtonBlock?> = registerWithItem<ButtonBlock?>(
            "wisteria_button"
        ) { ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get()!!.noCollission()) }

        @JvmField
        val WISTERIA_FENCE: Supplier<FenceBlock?> = registerWithItem<FenceBlock?>(
            "wisteria_fence"
        ) { FenceBlock(normalWoodProps.get()!!) }

        @JvmField
        val WISTERIA_FENCE_GATE: Supplier<FenceGateBlock?> = registerWithItem<FenceGateBlock?>(
            "wisteria_fence_gate"
        ) { FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val WISTERIA_DOOR: Supplier<DoorBlock?> = registerWithItem<DoorBlock?>(
            "wisteria_door", renderType = RenderType.CUTOUT
        ) { doorBasedOnSpruce() }

        @JvmField
        val WISTERIA_TRAPDOOR: Supplier<TrapDoorBlock?> = registerWithItem<TrapDoorBlock?>(
            "wisteria_trapdoor", renderType = RenderType.CUTOUT
        ) {
            trapdoorFromSpruce()
        }

        @JvmField
        val PHLOX: Supplier<Block?> = registerWithItem<Block?>(
            "phlox", renderType = RenderType.CUTOUT
        ) { PinkPetalsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS)) }

        // mush
        @JvmField
        val REEDS: Supplier<Block?> = registerWithItem<Block?>(
            "reeds", RenderType.CUTOUT_MIPPED
        ) { ReedsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)) }

        @JvmField
        val RED_MINI_SHROOM_SPORELING: Supplier<Block?> = registerWithItem<Block?>(
            "red_mini_shroom_sporeling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.RED_MINI_SHROOM_GROWER,
                BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.MUD)
                    .pushReaction(PushReaction.DESTROY)
            )
        }

        @JvmField
        val POTTED_RED_MINI_SHROOM_SPORELING: Supplier<FlowerPotBlock?> = registerFlowerPot(RED_MINI_SHROOM_SPORELING)

        @JvmField
        val BROWN_MINI_SHROOM_SPORELING: Supplier<Block?> = registerWithItem<Block?>(
            "brown_mini_shroom_sporeling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.BROWN_MINI_SHROOM_GROWER,
                BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.MUD)
                    .pushReaction(PushReaction.DESTROY)
            )
        }

        @JvmField
        val POTTED_BROWN_MINI_SHROOM_SPORELING: Supplier<FlowerPotBlock?> =
            registerFlowerPot(BROWN_MINI_SHROOM_SPORELING)

        // witchy
        @JvmField
        val WITCHY_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "witchy_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.WITCHY_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_WITCHY_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(WITCHY_SAPLING)

        // lupine
        @JvmField
        val LUPINE: Supplier<Block?> = registerWithItem<Block?>(
            "lupine", renderType = RenderType.CUTOUT_MIPPED
        ) {
            FlowerBlockNonShifting(
                MobEffects.POISON, 0.35f, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.NONE)
                    .pushReaction(PushReaction.DESTROY)
            )
        }

        @JvmField
        val POTTED_LUPINE: Supplier<FlowerPotBlock?> = registerFlowerPot(LUPINE)

        // alpine
        @JvmField
        val ALPINE_SPRUCE_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "alpine_spruce_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.ALPINE_SPRUCE_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_ALPINE_SPRUCE_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(ALPINE_SPRUCE_SAPLING)

        // ashen
        @JvmField
        val VOLCANIC_ASH: Supplier<Block?> = registerWithItem<Block?>(
            "volcanic_ash"
        ) {
            ColoredFallingBlock(
                ColorRGBA(2170911),
                BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
            )
        }

        // satis
        @JvmField
        val STRIPPED_SATISTREE_LOG: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_satistree_log"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG)) }

        @JvmField
        val SATISTREE_LOG: Supplier<RotatedPillarBlock?> = registerStrippablePillarBlock(
            "satistree_log",
            STRIPPED_SATISTREE_LOG, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG)
        )

        @JvmField
        val SATISTREE_WOOD: Supplier<Block?> = registerWithItem<Block?>(
            "satistree_wood"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD)) }

        @JvmField
        val STRIPPED_SATISTREE_WOOD: Supplier<Block?> = registerWithItem<Block?>(
            "stripped_satistree_wood"
        ) { FlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD)) }

        @JvmField
        val SATISTREE_PLANKS: Supplier<Block?> = registerWithItem<Block?>(
            "satistree_planks"
        ) { FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)) }

        @JvmField
        val ALIEN_LEAVES: Supplier<Block?> = registerWithItem<Block?>(
            "alien_leaves"
        ) { AlienLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)) }

        @JvmField
        val SATISTREE_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "satistree_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.SATISTREE_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_SATISTREE_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(SATISTREE_SAPLING)

        @JvmField
        val GIGANTIC_SATISTREE_SPROUTS: Supplier<Block?> = registerWithItem<Block?>(
            "gigantic_satistree_sprouts", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.GIGANTIC_SATISTREE_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        // non-full block stuff
        @JvmField
        val SATISTREE_STAIRS: Supplier<StairBlock?> = registerWithItem<StairBlock?>(
            "satistree_stairs"
        ) { StairBlock(SATISTREE_PLANKS.get()!!.defaultBlockState(), normalWoodProps.get()!!) }

        @JvmField
        val SATISTREE_SLAB: Supplier<SlabBlock?> = registerWithItem<SlabBlock?>(
            "satistree_slab"
        ) { SlabBlock(normalWoodProps.get()!!) }

        @JvmField
        val SATISTREE_PRESSURE_PLATE: Supplier<PressurePlateBlock?> = registerWithItem<PressurePlateBlock?>(
            "satistree_pressure_plate"
        ) { PressurePlateBlock(BlockSetType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val SATISTREE_BUTTON: Supplier<ButtonBlock?> = registerWithItem<ButtonBlock?>(
            "satistree_button"
        ) { ButtonBlock(BlockSetType.SPRUCE, 30, normalWoodProps.get()!!.noCollission()) }

        @JvmField
        val SATISTREE_FENCE: Supplier<FenceBlock?> = registerWithItem<FenceBlock?>(
            "satistree_fence"
        ) { FenceBlock(normalWoodProps.get()!!) }

        @JvmField
        val SATISTREE_FENCE_GATE: Supplier<FenceGateBlock?> = registerWithItem<FenceGateBlock?>(
            "satistree_fence_gate"
        ) { FenceGateBlock(WoodType.SPRUCE, normalWoodProps.get()!!) }

        @JvmField
        val SATISTREE_DOOR: Supplier<DoorBlock?> = registerWithItem<DoorBlock?>(
            "satistree_door", renderType = RenderType.CUTOUT
        ) {
            doorBasedOnSpruce()
        }

        @JvmField
        val SATISTREE_TRAPDOOR: Supplier<TrapDoorBlock?> = registerWithItem<TrapDoorBlock?>(
            "satistree_trapdoor", renderType = RenderType.CUTOUT
        ) {
            trapdoorFromSpruce()
        }

        @JvmField
        val ALIEN_PHYLLOSTACHYS_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "alien_phyllostachys_sapling", renderType = RenderType.CUTOUT
        ) { AlienPhyllostachysSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING)) }

        @JvmField
        val ALIEN_PHYLLOSTACHYS: Supplier<Block?> = registerWithItem<Block?>(
            "alien_phyllostachys"
        ) { AlienPhyllostachysStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO)) }

        @JvmField
        val POTTED_ALIEN_PHYLLOSTACHYS: Supplier<FlowerPotBlock?> = registerFlowerPot(ALIEN_PHYLLOSTACHYS)

        @JvmField
        val ALIEN_FENCE_PLANKS: Supplier<Block?> = registerWithItem<Block?>(
            "alien_fence_planks"
        ) { FlammablePlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)) }

        // chewy on the inside!
        @JvmField
        val ALIEN_FENCE_PLANT: Supplier<FenceBlock?> = registerWithItem<FenceBlock?>(
            "alien_fence_plant"
        ) {
            FenceBlock(
                BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BANJO)
                    .strength(2.0f, 3.0f)
                    .sound(SoundType.WET_GRASS)
                    .ignitedByLava()
            )
        }

        @JvmField
        val ALIEN_FENCE_PLANT_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "alien_fence_plant_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.ALIEN_FENCE_PLANT_GROWER,
                BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.WET_GRASS)
                    .pushReaction(PushReaction.DESTROY)
            )
        }

        @JvmField
        val POTTED_ALIEN_FENCE_PLANT_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(ALIEN_FENCE_PLANT_SAPLING)

        // shrub
        @JvmField
        val SHRUB_SAPLING: Supplier<Block?> = registerWithItem<Block?>(
            "shrub_sapling", renderType = RenderType.CUTOUT
        ) {
            SaplingBlock(
                ModTreeGrowers.SHRUB_GROWER,
                BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)
            )
        }

        @JvmField
        val POTTED_SHRUB_SAPLING: Supplier<FlowerPotBlock?> = registerFlowerPot(SHRUB_SAPLING)

        // methods

        private fun registerLeaves(
            key: String, props:
            BlockBehaviour.Properties = BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES),
            constructor: (BlockBehaviour.Properties) -> LeavesBlock = ::FlammableLeavesBlock
        ): Supplier<Block?> {
            val leaves: Supplier<Block?> = registerWithItem<Block?>(
                key, renderType = RenderType.CUTOUT_MIPPED) { constructor(props) }

            MOD_LEAVES.add(leaves);
            return leaves;
        }

        private fun registerStrippablePillarBlock(
            name: String?,
            stripped: Supplier<Block?>?,
            props: BlockBehaviour.Properties?
        ): Supplier<RotatedPillarBlock?> {
            val memoized = Suppliers.memoize<RotatedPillarBlock?>(Services.PLATFORM.createLoaderStrippableLog(props))
            MOD_STRIPPABLES[memoized] = stripped
            registerWithItem<RotatedPillarBlock?>(name, block = memoized)
            return memoized
        }

        // friendly reminder to do this with Neo's methods too!
        @JvmStatic
        fun <B : Block?> registerFabricFlammability(block: Supplier<B?>?, burn: Int, spread: Int) {
            FABRIC_MOD_FLAMMABLE_BLOCKS[block] = FlammabilityData(burn, spread)
        }

        private fun registerFlowerPot(plant: Supplier<Block?>): Supplier<FlowerPotBlock?> {
            val plantName = Utils.findBlockIdByMap(plant)
            val pot = register<FlowerPotBlock?>(
                "potted_$plantName", renderType = RenderType.CUTOUT,
                block = Suppliers.memoize {
                    FlowerPotBlock(
                        plant.get()!!, BlockBehaviour.Properties.ofFullCopy(
                            Blocks.POTTED_SPRUCE_SAPLING
                        )
                    )
                }
            )
            MOD_FLOWER_POTS[plant] = pot
            return pot
        }

        fun <B : Block?> registerWithItem(
            key: String?,
            renderType: RenderType = RenderType.NONE,
            block: Supplier<B?>
        ): Supplier<B?> {
            val memoized = Suppliers.memoize(block)
            register(key, renderType, memoized)
            ModItems.register(key) { BlockItem(memoized.get()!!, Item.Properties()) }
            return memoized
        }

        @Suppress("UNCHECKED_CAST")
        fun <B : Block?> register(
            key: String?,
            renderType: RenderType = RenderType.NONE,
            block: Supplier<B?>
        ): Supplier<B?> {
            if (renderType == RenderType.CUTOUT) MOD_CUTOUT_BLOCKS.add(block as Supplier<Block?>)
            if (renderType == RenderType.CUTOUT_MIPPED) MOD_CUTOUT_MIPPED_BLOCKS.add(block as Supplier<Block?>)
            if (renderType == RenderType.TRANSLUCENT) MOD_TRANSLUCENT_BLOCKS.add(block as Supplier<Block?>)

            val memoized = Suppliers.memoize(block)
            BLOCK_MAP[key] = memoized
            return memoized
        }

    }

    @JvmRecord
    data class FlammabilityData(val burn: Int, val spread: Int)
}
