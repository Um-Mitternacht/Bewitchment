package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

@SuppressWarnings("EmptyMethod")
public class ModSounds {
	public static final SoundEvent BROOM_SWEEP = createSoundEvent("broom_sweep");
	public static final SoundEvent CHALK_SCRIBBLE = createSoundEvent("chalk_scribble");
	public static final SoundEvent OWL_HOOT = createSoundEvent("owl_hoot");
	public static final SoundEvent RAVEN_CRY = createSoundEvent("raven_cry");
	public static final SoundEvent TOAD_DEATH = createSoundEvent("toad_death");
	public static final SoundEvent TOAD_HURT = createSoundEvent("toad_hurt");
	public static final SoundEvent TOAD_IDLE = createSoundEvent("toad_idle");
	public static final SoundEvent CLEAVER_DEATH = createSoundEvent("cleaver_death");
	public static final SoundEvent CLEAVER_HURT = createSoundEvent("cleaver_hurt");
	public static final SoundEvent CLEAVER_IDLE = createSoundEvent("cleaver_living");
	public static final SoundEvent BAFOMETYR_DEATH = createSoundEvent("bafometyr_death");
	public static final SoundEvent BAFOMETYR_HURT = createSoundEvent("bafometyr_hurt");
	public static final SoundEvent BAFOMETYR_IDLE = createSoundEvent("bafometyr_living");
	public static final SoundEvent WEREWOLF_HOWL = createSoundEvent("werewolf_howl");
	
	private static SoundEvent createSoundEvent(String name) {
		ResourceLocation id = new ResourceLocation(Bewitchment.MODID, name);
		return new SoundEvent(id).setRegistryName(id);
	}
}