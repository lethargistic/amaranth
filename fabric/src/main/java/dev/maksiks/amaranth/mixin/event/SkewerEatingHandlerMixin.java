package dev.maksiks.amaranth.mixin.event;

import dev.maksiks.amaranth.event.SkewerEatingSharedEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Hoglin.class, Wolf.class})
public class SkewerEatingHandlerMixin {
    @Inject(
            method = "mobInteract",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onInteractAt(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Animal animal = (Animal) (Object) this;
        ItemStack item = player.getItemInHand(hand);
        SkewerEatingSharedEvent.interact(animal, player, item);
    }
}
