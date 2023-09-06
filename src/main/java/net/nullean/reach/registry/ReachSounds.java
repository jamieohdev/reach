package net.nullean.reach.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;

public class ReachSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Reach.MOD_ID);

    public static final RegistryObject<SoundEvent> REACH_EXAMPLE_BGM = null;
    public static final RegistryObject<SoundEvent> MUSIC_BLEMISH = registerSoundEvent("music.theashfalls");
    public static final RegistryObject<SoundEvent> MUSIC_PILLARS = registerSoundEvent("music.glimmer");
    public static final RegistryObject<SoundEvent> MUSIC_SOUL_PLAINS = registerSoundEvent("music.coda");
    public static final RegistryObject<SoundEvent> MUSIC_CALM = registerSoundEvent("music.rodhim");
    public static final RegistryObject<SoundEvent> MUSIC_BARRENS = registerSoundEvent("music.gelida");

    private static RegistryObject<SoundEvent> registerSoundEvent(String path) {
        return SOUND_EVENTS.register(path, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Reach.MOD_ID, path)));
    }

}
