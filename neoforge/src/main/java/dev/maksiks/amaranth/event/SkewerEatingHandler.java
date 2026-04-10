package dev.maksiks.amaranth.event;

import dev.maksiks.amaranth.Constants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class SkewerEatingHandler {
    @SubscribeEvent
    public static void onInteracted(PlayerInteractEvent.EntityInteractSpecific e) {
        Entity target = e.getTarget();
        ItemStack item = e.getItemStack();
        Player player = e.getEntity();
        if (target instanceof Animal) {
            SkewerEatingSharedEvent.interact((Animal) target, player, item);
        }
    }
}
