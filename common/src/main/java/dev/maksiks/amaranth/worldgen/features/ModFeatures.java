package dev.maksiks.amaranth.worldgen.features;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.worldgen.features.tree.GiganticSatistreeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

import java.util.HashMap;

public class ModFeatures {
    public static final HashMap<String, Supplier<? extends Feature<?>>> FEATURE_MAP = new HashMap<>();

    // TODO mov: originally used DeferredHolder unlike other registries, investigate if i still have type safety
    public static final Supplier<Feature<NoneFeatureConfiguration>> DESOLATE_SPIKE_FEATURE =
            register("desolate_spike",
                    () -> new DesolateSpikeFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> ORDERLY_COURTS_RUINS_FEATURE =
            register("orderly_courts_ruins",
                    () -> new OrderlyCourtsRuinsFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<SimpleBlockConfiguration>> SPIKY_ARCHES_FILL_FEATURE =
            register("spiky_arches_fill",
                    () -> new SpikyArchesFillFeature(SimpleBlockConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> THICK_PUMPKIN_FEATURE =
            register("thick_pumpkin",
                    () -> new ThickPumpkinFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> MUSH_REEDS_FEATURE =
            register("mush_reeds",
                    () -> new MushReedsWaterFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> ALPINE_BOULDER_FEATURE =
            register("alpine_boulder",
                    () -> new AlpineBoulderFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> GIGANTIC_SATISTREE_FEATURE =
            register("gigantic_satistree",
                    () -> new GiganticSatistreeFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> ALIEN_PHYLLOSTACHYS_FEATURE =
            register("alien_phyllostachys",
                    () -> new AlienPhyllostachysFeature(ProbabilityFeatureConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> SATIS_ROCK_FEATURE =
            register("satis_rock",
                    () -> new SatisRockFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> SHRUBLAND_ROCK_FEATURE =
            register("shrubland_rock",
                    () -> new VividShrublandRockFeature(NoneFeatureConfiguration.CODEC));

    private static <T extends FeatureConfiguration> Supplier<Feature<T>> register(String key, Supplier<Feature<T>> feature) {
        Supplier<Feature<T>> memoized = Suppliers.memoize(feature);
        FEATURE_MAP.put(key, memoized);
        return Suppliers.memoize(memoized);
    }
}