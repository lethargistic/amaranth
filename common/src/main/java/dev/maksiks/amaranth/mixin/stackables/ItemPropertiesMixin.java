package dev.maksiks.amaranth.mixin.stackables;

import dev.maksiks.amaranth.component.ModDataComponentTypes;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// disables stackability check for my stackable tools
// this either breaks something obscure and i don't care
// or breaks something serious and i should stop
@Mixin(Item.Properties.class)
public class ItemPropertiesMixin {
    @Inject(method = "buildAndValidateComponents", at = @At("HEAD"), cancellable = true)
    private void skipStackableDurabilityCheck(CallbackInfoReturnable<DataComponentMap> cir) {
        DataComponentMap map = ((ItemPropertiesAccessor) this).invoker$buildComponents();
        if (map.has(ModDataComponentTypes.STACKABLE_TIERED.get())) {
            cir.setReturnValue(map);
        }
    }

    @Redirect(
            method = "durability",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;component(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 1 // order specific oh well TODO newver: check if broke
            )
    )
    private <T> Item.Properties redirectStackSize(Item.Properties self, DataComponentType<T> type, T value) {
        if (type == DataComponents.MAX_STACK_SIZE) {
            DataComponentMap.Builder components = ((ItemPropertiesAccessor) self).getComponents();
            if (components != null && components.build().has(ModDataComponentTypes.STACKABLE_TIERED.get())) {
                return self;
            }
        }
        return self.component(type, value);
    }
}