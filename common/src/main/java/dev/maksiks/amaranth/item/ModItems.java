package dev.maksiks.amaranth.item;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.entity.ModEntities;
import dev.maksiks.amaranth.item.custom.CrownOfThornsItem;
import dev.maksiks.amaranth.item.custom.MelonHelmetItem;
import dev.maksiks.amaranth.item.custom.WisteriaJuiceItem;
import dev.maksiks.amaranth.platform.Services;
import dev.maksiks.amaranth.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.HashMap;
import java.util.List;

public class ModItems {
    public static final HashMap<String, Supplier<? extends Item>> ITEM_MAP = new HashMap<>();

    // misc
    public static final Supplier<Item> MAFIA_BLOB = register("mafia_blob",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.amaranth.mafia_blob.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final Supplier<Item> BEANIE_BLOB = register("beanie_blob",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HEXFRUIT = register("hexfruit",
            () -> new Item(new Item.Properties().food(ModFoodProperties.HEXFRUIT)));

    public static final Supplier<Item> SHROOM_BOI_SPAWN_EGG = register("shroom_boi_spawn_egg",
            () -> Services.PLATFORM.createSpawnEggItem(ModEntities.SHROOM_BOI, 0xf75d57, 0xf1f1f1,
                    new Item.Properties()));

    public static final Supplier<Item> EMPTY_TEA_CUP = register("empty_tea_cup",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MUSHROOM_TEA = register("mushroom_tea",
            () -> new Item(new Item.Properties().food(ModFoodProperties.MUSHROOM_TEA)));

    public static final Supplier<ArmorItem> MELON_HELMET = register("melon_helmet",
            () -> new MelonHelmetItem(ModArmorMaterials.MELON_HELMET_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));

    // discs
    public static final Supplier<Item> PALETTE_OVERLOAD_MUSIC_DISC = register("palette_overload_music_disc",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.PALETTE_OVERLOAD_KEY).stacksTo(1)));

    // pain
    public static final Supplier<Item> THORN = register("thorn",
            () -> new Item(new Item.Properties()));
    public static final Supplier<ArmorItem> CROWN_OF_THORNS = register("crown_of_thorns",
            () -> new CrownOfThornsItem(ModArmorMaterials.CROWN_OF_THORNS_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(9))));

    // pastel
    public static final Supplier<Item> WISTERIA_JUICE = register("wisteria_juice",
            () -> new WisteriaJuiceItem(new Item.Properties().food(ModFoodProperties.WISTERIA_JUICE)));

    // mush
    public static final Supplier<Item> REED_BAR = register("reed_bar",
            () -> new Item(new Item.Properties().food(ModFoodProperties.REED_BAR)));

    public static <B extends Item> Supplier<B> register(String key, Supplier<B> item) {
        ITEM_MAP.put(key, item);
        return Suppliers.memoize(item);
    }

    // TODO mov: figure eggs out
}
