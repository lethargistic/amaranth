package dev.maksiks.amaranth.platform;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.Constants;
import dev.maksiks.amaranth.ModCreativeTabs;
import dev.maksiks.amaranth.block.custom.FlammableRotatedPillarBlock;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public Supplier<CreativeModeTab> createCreativeTab(String tabName, List<Supplier<? extends ItemLike>> items, Supplier<? extends ItemLike> icon) {
        ResourceKey<CreativeModeTab> creativeTabKey =
                ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, tabName));
        CreativeModeTab creativeTab = FabricItemGroup.builder()
                .icon(() -> new ItemStack(icon.get()))
                .title(Component.translatable(String.format("creativetab.amaranth.%s", tabName)))
                .build();

        ItemGroupEvents.modifyEntriesEvent(creativeTabKey).register(itemGroup -> {
            items.forEach(item -> itemGroup.accept(item.get().asItem()));
        });

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, creativeTabKey, creativeTab);

        return () -> creativeTab;
    }

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
