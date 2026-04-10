package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.item.ModItems;
import dev.maksiks.amaranth.platform.Services;
import dev.maksiks.amaranth.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class SkewerEatingSharedEvent {
    public static void interact(Animal target, Player player, ItemStack item) {
        boolean hog = target instanceof Hoglin;
        boolean dog = target instanceof Wolf;
        if (!(item.is(ModItems.RAW_MEAT_SKEWER.get()) || item.is(ModItems.MEAT_SKEWER.get()))) return;
        if (dog || hog) {
            boolean needsHeals = target.getHealth() < target.getMaxHealth();
            if (!((needsHeals) ||
                    (!target.level().isClientSide && target.getAge() == 0 && target.canFallInLove()) ||
                    target.isBaby())) return;

            if (!(target.level() instanceof ServerLevel serverLevel)) return;

            // vanilla does anyway if they can mate because of the tag
            // but hogs don't have healing
            if (needsHeals && hog) {
                item.consume(1, player);
            }

            target.playSound(
                    SoundEvents.GENERIC_EAT,
                    1.0F,
                    1.0F);

            if (hog) {
                RandomSource random = RandomSource.create();
                if (random.nextBoolean()) {
                    target.playSound(
                            ModSounds.METAL_PIPE.get(),
                            0.2F,
                            1.0F);
                } else {
                    target.playSound(
                            ModSounds.EATING_METAL.get(),
                            1.0F,
                            1.0F);
                }

                for (int i = 0; i < 7; i++) {
                    double d0 = random.nextGaussian() * 0.02;
                    double d1 = random.nextGaussian() * 0.02;
                    double d2 = random.nextGaussian() * 0.02;
                    serverLevel.sendParticles(ParticleTypes.WAX_OFF, target.getRandomX(1.0), target.getRandomY() + 0.5, target.getRandomZ(1.0), 1, d0, d1, d2, 0);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, target.getRandomX(1.0), target.getRandomY() + 0.5, target.getRandomZ(1.0), 1, d0, d1, d2, 0);
                }
            }

            if (dog) {
                ItemEntity itemEntity = new ItemEntity(serverLevel, target.getX(), target.getY(), target.getZ(), new ItemStack(ModItems.SKEWER.get()));
                serverLevel.addFreshEntity(itemEntity);
            }

            // hardcoded because whatever
            target.heal(12);
        }
    }
}
