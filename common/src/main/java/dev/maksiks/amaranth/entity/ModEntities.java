package dev.maksiks.amaranth.entity;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.entity.custom.ShroomBoiEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.HashMap;

public class ModEntities {
    public static final HashMap<String, Supplier<? extends EntityType<?>>> ENTITY_TYPE_MAP = new HashMap<>();

    public static final Supplier<EntityType<ShroomBoiEntity>> SHROOM_BOI = register("shroom_boi", Suppliers.memoize(() -> EntityType.Builder.of(ShroomBoiEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.0f).build(Constants.MOD_ID + ":shroom_boi")));

    private static <T extends Entity> Supplier<EntityType<T>> register(String key, Supplier<EntityType<T>> entity) {
        ENTITY_TYPE_MAP.put(key, entity);
        return Suppliers.memoize(entity);
    }
}