package dev.maksiks.amaranth.platform;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.block.custom.FlammableRotatedPillarBlock;
import dev.maksiks.amaranth.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static dev.maksiks.amaranth.block.ModBlocks.MOD_STRIPPABLES;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public Supplier<RotatedPillarBlock> createLoaderStrippableLog(BlockBehaviour.Properties props) {
        return Suppliers.memoize(() -> new FlammableRotatedPillarBlock(props));
    }

    @Override
    public SpawnEggItem createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties props) {
        return new SpawnEggItem(type.get(), backgroundColor, highlightColor, props);
    }

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
