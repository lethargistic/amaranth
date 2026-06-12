package dev.maksiks.amaranth.entity.client;

import com.google.common.base.Supplier;
import dev.maksiks.amaranth.entity.ModEntitiesServer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import static dev.maksiks.amaranth.entity.ModEntitiesServer.ENTITY_TYPE_MAP;

///
/// split with {@link ModEntitiesServer}
///

public class ModEntitiesClient {
    public record EntityModelEntry(ModelLayerLocation loc, Supplier<LayerDefinition> def) {
    }

    public static final List<EntityModelEntry> ENTITY_MODELS = new ArrayList<>();

    public record EntityRendererEntry(Supplier<?> entity, EntityRendererProvider<?> renderer) {
    }

    public static final List<EntityRendererEntry> ENTITY_RENDERERS = new ArrayList<>();

    public static Boolean initialized = false;

    public static void initializeClientEntityData() {
        if (!initialized) {
            register("shroom_boi",
                    ShroomBoiModel.LAYER_LOCATION,
                    ShroomBoiModel::createBodyLayer,
                    ShroomBoiRenderer::new);

            initialized = true;
        }
    }

    private static <T extends Entity> void register(String key,
                                                    ModelLayerLocation layerLoc,
                                                    Supplier<LayerDefinition> layerDef,
                                                    EntityRendererProvider<T> renderer
    ) {
        ENTITY_MODELS.add(new EntityModelEntry(layerLoc, layerDef));
        ENTITY_RENDERERS.add(new EntityRendererEntry(ENTITY_TYPE_MAP.get(key), renderer));
    }
}
