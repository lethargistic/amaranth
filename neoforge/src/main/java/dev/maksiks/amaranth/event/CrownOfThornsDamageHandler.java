package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class CrownOfThornsDamageHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        ModSharedEvents.doLivingIncomingDamageEvent(target, source);
    }
}