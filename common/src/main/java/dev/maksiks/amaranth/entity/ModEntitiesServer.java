package dev.maksiks.amaranth.entity;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.entity.client.ShroomBoiModel;
import dev.maksiks.amaranth.entity.client.ShroomBoiRenderer;
import dev.maksiks.amaranth.entity.custom.ShroomBoiEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModEntitiesServer {
    public static final HashMap<String, Supplier<? extends EntityType<?>>> ENTITY_TYPE_MAP = new HashMap<>();
    public record EntityAttributeEntry(Supplier<?> entity, Supplier<AttributeSupplier.Builder> attributes) {}
    public static final List<EntityAttributeEntry> ENTITY_ATTRIBUTES = new ArrayList<>();

    public static final Supplier<EntityType<ShroomBoiEntity>> SHROOM_BOI = register("shroom_boi",
            () -> EntityType.Builder.of(ShroomBoiEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.0f).build(Constants.MOD_ID + ":shroom_boi"),
            ShroomBoiEntity::createAttributes);

    private static <T extends Entity> Supplier<EntityType<T>> register(String key, Supplier<EntityType<T>> entity,
                                                                       Supplier<AttributeSupplier.Builder> attributes
    ) {
        Supplier<EntityType<T>> memoized = Suppliers.memoize(entity);
        ENTITY_TYPE_MAP.put(key, memoized);
        ENTITY_ATTRIBUTES.add(new EntityAttributeEntry(memoized, attributes));
        return memoized;
    }
}