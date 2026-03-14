package dev.maksiks.amaranth.platform;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.block.ModBlocks;
import dev.maksiks.amaranth.block.custom.NeoFlammableRotatedPillarBlock;
import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.platform.services.IPlatformHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.List;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public Supplier<CreativeModeTab> createCreativeTab(String tabName, List<Supplier<? extends ItemLike>> items,
                                         Supplier<? extends ItemLike> icon) {
        return  () -> CreativeModeTab.builder().icon(() -> new ItemStack(icon.get()))
                .title(Component.translatable(String.format("creativetab.amaranth.%s", tabName)))
                .displayItems((itemDisplayParameters, output) -> {
                    items.forEach(item -> output.accept(item.get().asItem()));
                }).build();
    }

    @Override
    public Supplier<RotatedPillarBlock> createLoaderStrippableLog(BlockBehaviour.Properties props) {
        return Suppliers.memoize(() -> new NeoFlammableRotatedPillarBlock(props));
    }

    @Override
    public SpawnEggItem createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties props) {
        return new DeferredSpawnEggItem(type, backgroundColor, highlightColor, props);
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}