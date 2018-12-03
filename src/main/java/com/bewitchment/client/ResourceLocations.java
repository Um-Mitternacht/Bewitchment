package com.bewitchment.client;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ResourceLocations {

	//Misc
	public static final ResourceLocation GRAY_WATER = getLocation("blocks/fluid/gray_scale_fluid");

	//GUI
	public static final ResourceLocation POTION_TEXTURES = getLocation("textures/gui/potions.png");
	public static final ResourceLocation BREW_TEXTURES = getLocation("textures/gui/brews.png");
	public static final ResourceLocation APIARY_GUI = getLocation("textures/gui/apiary.png");
	public static final ResourceLocation OVEN_GUI = getLocation("textures/gui/oven.png");
	public static final ResourceLocation DISTILLERY_GUI = getLocation("textures/gui/distillery.png");
	public static final ResourceLocation THREAD_SPINNER_GUI = getLocation("textures/gui/thread_spinner.png");
	public static final ResourceLocation ENERGY_BACKGROUND_FILL = getLocation("textures/gui/energy_dark.png");
	public static final ResourceLocation ENERGY_BACKGROUND_PULSE = getLocation("textures/gui/energy_white.png");

	//Particles
	public static final ResourceLocation BUBBLE = getLocation("textures/particle/bubble.png");
	public static final ResourceLocation STEAM = getLocation("particle/steam");
	public static final ResourceLocation BEE = getLocation("particle/bee");
	public static final ResourceLocation BAT = getLocation("particle/bat");
	public static final ResourceLocation FLAME = getLocation("particle/flame");

	//Loot Tables

	private ResourceLocations() {
	}

	private static ResourceLocation getLocation(String name) {
		return new ResourceLocation(LibMod.MOD_ID, name);
	}
}
