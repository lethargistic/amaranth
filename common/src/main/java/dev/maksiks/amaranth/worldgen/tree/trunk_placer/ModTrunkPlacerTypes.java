package dev.maksiks.amaranth.worldgen.tree.trunk_placer;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.worldgen.tree.trunk_placer.custom_placer.*;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.HashMap;

public class ModTrunkPlacerTypes{
    public static final HashMap<String, Supplier<? extends TrunkPlacerType<?>>> TRUNK_PLACER_TYPE_MAP = new HashMap<>();

    public static final Supplier<TrunkPlacerType<MysticTrunkPlacer>> MYSTIC_TRUNK_PLACER = register("mystic_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(MysticTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<StubbyTrunkPlacer>> STUBBY_TRUNK_PLACER = register("stubby_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(StubbyTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<TreeOnTreeTreeTrunkPlacer>> TREE_ON_TREE_TREE_TRUNK_PLACER = register("tree_on_tree_tree_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(TreeOnTreeTreeTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<AnthocyaninTrunkPlacer>> ANTHOCYANIN_TRUNK_PLACER = register("anthocyanin_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(AnthocyaninTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<WisteriaTrunkPlacer>> WISTERIA_TRUNK_PLACER = register("wisteria_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(WisteriaTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<ModififedForkingTrunkPlacer>> MODIFIED_FORKING_TRUNK_PLACER = register("modified_forking_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(ModififedForkingTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<WitchyTrunkPlacer>> WITCHY_TRUNK_PLACER = register("witchy_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(WitchyTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<AlpineSpruceTrunkPlacer>> ALPINE_SPRUCE_TRUNK_PLACER = register("alpine_spruce_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(AlpineSpruceTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<SatistreeTrunkPlacer>> SATISTREE_TRUNK_PLACER = register("satistree_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(SatistreeTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<GiganticSatistreeTrunkPlacer>> GIGANTIC_SATISTREE_TRUNK_PLACER = register("gigantic_satistree_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(GiganticSatistreeTrunkPlacer.CODEC)));
    public static final Supplier<TrunkPlacerType<AlienFencePlantTrunkPlacer>> ALIEN_FENCE_PLANT_TRUNK_PLACER = register("alien_fence_plant_trunk_placer", Suppliers.memoize(() -> new TrunkPlacerType<>(AlienFencePlantTrunkPlacer.CODEC)));

    private static <T extends TrunkPlacer> Supplier<TrunkPlacerType<T>> register(String key, Supplier<TrunkPlacerType<T>> placer) {
        TRUNK_PLACER_TYPE_MAP.put(key, placer);
        return Suppliers.memoize(placer);
    }}
