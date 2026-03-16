package dev.maksiks.amaranth.particle;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.HashMap;
import java.util.function.Function;

public class ModParticles {

    public static final HashMap<String, Supplier<? extends ParticleType<?>>> PARTICLE_TYPE_MAP = new HashMap<>();

    public static final Supplier<SimpleParticleType> SILVER_BIRCH_PARTICLES =
            register("silver_birch_particles",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> ANTHOCYANIN_PARTICLES =
            register("anthocyanin_particles",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> WISTERIA_PARTICLES =
            register("wisteria_particles",
            () -> new SimpleParticleType(false));

    public static <P extends ParticleType<?>> Supplier<P> register(String key, Supplier<P> particle) {
        Supplier<P> memoized = Suppliers.memoize(particle);
        PARTICLE_TYPE_MAP.put(key, memoized);
        return memoized;
    }
}
