package com.bewitchment.common.abilities;

import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ModAbilities {

	public static final HotbarAction NIGHT_VISION = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "night_vision")) {
		
		@Override
		public int getIconIndexY(EntityPlayer player) {
			int yIndex = super.getIconIndexY(player);
			if (player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive())
				return yIndex;
			return yIndex + 1;
			
		}
		
	}.setIconIndexes(0, 0);
	public static final HotbarAction DRAIN_BLOOD = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "drain_blood")).setIconIndexes(1, 0);

	private ModAbilities() {
	}

	public static void dummyMethodToLoadClass() {
	}

}
