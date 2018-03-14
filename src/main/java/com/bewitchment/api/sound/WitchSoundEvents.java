package com.bewitchment.api.sound;

import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class WitchSoundEvents {

	public static final SoundEvent BOIL = getRegisteredSound("boil");
	public static final SoundEvent BUZZ = getRegisteredSound("buzz");
	public static final SoundEvent CHALK_SCRIBBLE = getRegisteredSound("chalk_scribble");
	public static final SoundEvent BUBBLE = getRegisteredSound("bubble");
	public static final SoundEvent OVEN_OPEN = getRegisteredSound("oven_open");

	private WitchSoundEvents() {
	}

	private static SoundEvent getRegisteredSound(String name) {
		return SoundEvent.REGISTRY.getObject(new ResourceLocation(LibMod.MOD_ID, name));
	}
}
