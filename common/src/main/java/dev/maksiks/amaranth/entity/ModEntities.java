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

public class ModEntities {
    public static final HashMap<String, Supplier<? extends EntityType<?>>> ENTITY_TYPE_MAP = new HashMap<>();
    public record EntityModelEntry(ModelLayerLocation loc, Supplier<LayerDefinition> def) {}
    public static final List<EntityModelEntry> ENTITY_MODELS = new ArrayList<>();
    public record EntityAttributeEntry(Supplier<?> entity, Supplier<AttributeSupplier.Builder> attributes) {}
    public static final List<EntityAttributeEntry> ENTITY_ATTRIBUTES = new ArrayList<>();
    public record EntityRendererEntry(Supplier<?> entity, EntityRendererProvider<?> renderer) {}
    public static final List<EntityRendererEntry> ENTITY_RENDERERS = new ArrayList<>();

    public static final Supplier<EntityType<ShroomBoiEntity>> SHROOM_BOI = register("shroom_boi",
            () -> EntityType.Builder.of(ShroomBoiEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.0f).build(Constants.MOD_ID + ":shroom_boi"),
            ShroomBoiModel.LAYER_LOCATION,
            ShroomBoiEntity::createAttributes,
            ShroomBoiModel::createBodyLayer,
            ShroomBoiRenderer::new);

    private static <T extends Entity> Supplier<EntityType<T>> register(String key, Supplier<EntityType<T>> entity,
                                                                       ModelLayerLocation layerLoc,
                                                                       Supplier<AttributeSupplier.Builder> attributes,
                                                                       Supplier<LayerDefinition> layerDef,
                                                                       EntityRendererProvider<T> renderer
    ) {
        Supplier<EntityType<T>> memoized = Suppliers.memoize(entity);
        ENTITY_TYPE_MAP.put(key, memoized);
        ENTITY_ATTRIBUTES.add(new EntityAttributeEntry(memoized, attributes));
        ENTITY_MODELS.add(new EntityModelEntry(layerLoc, layerDef));
        ENTITY_RENDERERS.add(new EntityRendererEntry(memoized, renderer));
        return memoized;
    }
}