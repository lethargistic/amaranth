package dev.maksiks.amaranth.mixin.event;

import dev.maksiks.amaranth.event.ModSharedEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// recreating Neo's hook
@Mixin(LivingEntity.class)
public abstract class CrownOfThornsDamageHandlerMixin {
    @Inject(
            method = "hurt",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z",
                    ordinal = 0
            )
    )
    private void onLivingIncomingDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ModSharedEvents.doLivingIncomingDamageEvent((LivingEntity) (Object) this, source);
    }
}
