package com.bewitchment.common.abilities;

import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;

public class ModAbilities {

	public static final HotbarAction NIGHT_VISION = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "night_vision")).setIconIndexes(0, 0);

	private ModAbilities() {
	}

}
