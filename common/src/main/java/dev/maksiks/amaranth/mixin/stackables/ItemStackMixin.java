package dev.maksiks.amaranth.mixin.stackables;

import com.mojang.serialization.DataResult;
import dev.maksiks.amaranth.component.ModDataComponentTypes;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// technically skips item stack larger than maximum validation stage
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "validateComponents", at = @At("HEAD"), cancellable = true)
    private static void skipDamageableStackableCheck(DataComponentMap components, CallbackInfoReturnable<DataResult<Unit>> cir) {
        if (components.has(ModDataComponentTypes.STACKABLE_TIERED.get())) {
            cir.setReturnValue(DataResult.success(Unit.INSTANCE));
        }
    }
}