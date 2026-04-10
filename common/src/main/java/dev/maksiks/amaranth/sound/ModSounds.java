package dev.maksiks.amaranth.sound;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.maksiks.amaranth.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

import java.util.HashMap;

public class ModSounds {
    public static final HashMap<String, com.google.common.base.Supplier<SoundEvent>> SOUND_EVENT_MAP = new HashMap<>();

    public static final Supplier<SoundEvent> ARCTIC_WIND_THRONGLED = registerSoundEvent("arctic_wind_throngled");
    public static final Supplier<SoundEvent> METAL_PIPE = registerSoundEvent("metal_pipe");
    public static final Supplier<SoundEvent> EATING_METAL = registerSoundEvent("eating_metal");

    public static Holder.Reference<SoundEvent> getSoundHolder(Supplier<SoundEvent> supplier) {
        return BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(
                BuiltInRegistries.SOUND_EVENT.getResourceKey(supplier.get())
                        .orElseThrow()
        );
    }

    public static final Supplier<SoundEvent> PALETTE_OVERLOAD = registerSoundEvent("palette_overload");
    public static final ResourceKey<JukeboxSong> PALETTE_OVERLOAD_KEY = createSong("palette_overload");

    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
        Supplier<SoundEvent> event = () -> SoundEvent.createVariableRangeEvent(id);
        SOUND_EVENT_MAP.put(name, event);
        return Suppliers.memoize(event);
    }
}
