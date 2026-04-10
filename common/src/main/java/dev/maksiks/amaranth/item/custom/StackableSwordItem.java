package dev.maksiks.amaranth.item.custom;

import dev.maksiks.amaranth.component.ModDataComponentTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class StackableSwordItem extends SwordItem {
    public StackableSwordItem(Tier tier, int stacksTo, Properties properties) {
        super(tier, properties
                .component(DataComponents.MAX_STACK_SIZE, stacksTo)
                .component(ModDataComponentTypes.STACKABLE_TIERED.get(), true)
        );
    }
}
