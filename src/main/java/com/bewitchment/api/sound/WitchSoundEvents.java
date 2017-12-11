package com.bewitchment.api.sound;

import com.bewitchment.api.WitchcraftAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class WitchSoundEvents {

	public static final SoundEvent BOIL = getRegisteredSound(WitchcraftAPI.BOIL);
	public static final SoundEvent BUZZ = getRegisteredSound(WitchcraftAPI.BUZZ);
	public static final SoundEvent CHALK_SCRIBBLE = getRegisteredSound(WitchcraftAPI.CHALK_SCRIBBLE);
	public static final SoundEvent BUBBLE = getRegisteredSound(WitchcraftAPI.BUBBLE);
	public static final SoundEvent OVEN_OPEN = getRegisteredSound(WitchcraftAPI.OVEN_OPEN);

	private WitchSoundEvents() {
	}

	private static SoundEvent getRegisteredSound(ResourceLocation name) {
		return SoundEvent.REGISTRY.getObject(name);
	}
}
