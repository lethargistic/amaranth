package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ModSharedEvents {
    public static void doLivingIncomingDamageEvent(LivingEntity target, DamageSource source) {
        ItemStack headItem = target.getItemBySlot(EquipmentSlot.HEAD);
        if (headItem.getItem() == ModItems.CROWN_OF_THORNS.get()) {
            if (source.getEntity() instanceof LivingEntity attacker && attacker.isAlive()) {
                // essentially thorns 3 sorta
                if (target.getRandom().nextFloat() < 0.45f) {
                    float thornsDamage = 1.0F + target.getRandom().nextInt(4);
                    attacker.hurt(target.damageSources().thorns(target), thornsDamage);
                }

                attacker.level().levelEvent(2001, attacker.blockPosition(), 0);
            }
        }
    }

}
