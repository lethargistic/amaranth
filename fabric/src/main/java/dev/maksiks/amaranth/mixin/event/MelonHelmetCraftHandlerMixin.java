package dev.maksiks.amaranth.mixin.event;

import dev.maksiks.amaranth.event.MelonHelmetCraftHandlerSharedEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// recreating Neo's hook
@Mixin(ResultSlot.class)
public class MelonHelmetCraftHandlerMixin {
    @Final
    @Shadow
    private Player player;

    @Inject(
            method = "checkTakeAchievements",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;onCraftedBy(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;I)V",
                    shift = At.Shift.AFTER
            )
    )
    private void onItemCrafted(ItemStack stack, CallbackInfo ci) {
        MelonHelmetCraftHandlerSharedEvent.itemCrafted(this.player, stack);
    }
}
