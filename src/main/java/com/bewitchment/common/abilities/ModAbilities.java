package com.bewitchment.common.abilities;

import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;

public class ModAbilities {

	public static final HotbarAction NIGHT_VISION = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "night_vision")).setIconIndexes(0, 0);
	public static final HotbarAction DRAIN_BLOOD = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "drain_blood")).setIconIndexes(1, 0);

	private ModAbilities() {
	}

	public static void dummyMethodToLoadClass() {
	}

}
