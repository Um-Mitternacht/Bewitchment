package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSounds {
	public static final SoundEvent BROOM_SWEEP = createSoundEvent("broom_sweep");
	public static final SoundEvent CHALK_SCRIBBLE = createSoundEvent("chalk_scribble");
	public static final SoundEvent OWL_HOOT = createSoundEvent("owl_hoot");
	public static final SoundEvent RAVEN_CRY = createSoundEvent("raven_cry");
	public static final SoundEvent TOAD_DEATH = createSoundEvent("toad_death");
	public static final SoundEvent TOAD_HURT = createSoundEvent("toad_hurt");
	public static final SoundEvent TOAD_IDLE = createSoundEvent("toad_idle");
	
	public static void preInit() {
	}
	
	private static SoundEvent createSoundEvent(String name) {
		ResourceLocation id = new ResourceLocation(Bewitchment.MODID, name);
		SoundEvent event = new SoundEvent(id).setRegistryName(id);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}