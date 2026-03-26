package dev.maksiks.amaranth.worldgen.tree.foliage_placer;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.worldgen.tree.foliage_placer.custom_placer.*;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.HashMap;

public class ModFoliagePlacerTypes{
    public static final HashMap<String, Supplier<? extends FoliagePlacerType<?>>> FOLIAGE_PLACER_TYPE_MAP = new HashMap<>();

    public static final Supplier<FoliagePlacerType<MysticFoliagePlacer>> MYSTIC_FOLIAGE_PLACER = register("mystic_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(MysticFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<StubbyFoliagePlacer>> STUBBY_FOLIAGE_PLACER = register("stubby_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(StubbyFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<SilverBirchFoliagePlacer>> SILVER_BIRCH_FOLIAGE_PLACER = register("silver_birch_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(SilverBirchFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<TrimmedTreeFoliagePlacer>> TRIMMED_TREE_FOLIAGE_PLACER = register("trimmed_tree_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(TrimmedTreeFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<TreeOnTreeTreeFoliagePlacer>> TREE_ON_TREE_TREE_FOLIAGE_PLACER = register("tree_on_tree_tree_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(TreeOnTreeTreeFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<AnthocyaninFoliagePlacer>> ANTHOCYANIN_FOLIAGE_PLACER = register("anthocyanin_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(AnthocyaninFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<SpearyFoliagePlacer>> SPEARY_FOLIAGE_PLACER = register("speary_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(SpearyFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<WitchyFoliagePlacer>> WITCHY_FOLIAGE_PLACER = register("witchy_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(WitchyFoliagePlacer.CODEC)));
    public static final Supplier<FoliagePlacerType<AlpineSpruceFoliagePlacer>> ALPINE_SPRUCE_FOLIAGE_PLACER = register("alpine_spruce_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(AlpineSpruceFoliagePlacer.getCODEC())));
    public static final Supplier<FoliagePlacerType<SatistreeFoliagePlacer>> SATISTREE_FOLIAGE_PLACER = register("satistree_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(SatistreeFoliagePlacer.getCODEC())));
    public static final Supplier<FoliagePlacerType<GiganticSatistreeFoliagePlacer>> GIGANTIC_SATISTREE_FOLIAGE_PLACER = register("gigantic_satistree_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(GiganticSatistreeFoliagePlacer.getCODEC())));
    public static final Supplier<FoliagePlacerType<AlienFencePlantFoliagePlacer>> ALIEN_FENCE_PLANT_FOLIAGE_PLACER = register("alien_fence_plant_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(AlienFencePlantFoliagePlacer.getCODEC())));
    public static final Supplier<FoliagePlacerType<ShrubFoliagePlacer>> SHRUB_FOLIAGE_PLACER = register("shrub_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(ShrubFoliagePlacer.getCODEC())));
    public static final Supplier<FoliagePlacerType<FrontierBlossomFoliagePlacer>> FRONTIER_BLOSSOM_FOLIAGE_PLACER = register("frontier_blossom_foliage_placer", Suppliers.memoize(() -> new FoliagePlacerType<>(FrontierBlossomFoliagePlacer.getCODEC())));

    private static <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> register(String key, Supplier<FoliagePlacerType<T>> placer) {
        FOLIAGE_PLACER_TYPE_MAP.put(key, placer);
        return Suppliers.memoize(placer);
    }
}
