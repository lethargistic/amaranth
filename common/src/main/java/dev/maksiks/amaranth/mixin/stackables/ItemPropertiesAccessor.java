package dev.maksiks.amaranth.mixin.stackables;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(Item.Properties.class)
public interface ItemPropertiesAccessor {
    @Invoker("buildComponents")
    DataComponentMap invoker$buildComponents();

    @Accessor("components")
    @Nullable
    DataComponentMap.Builder getComponents();
}