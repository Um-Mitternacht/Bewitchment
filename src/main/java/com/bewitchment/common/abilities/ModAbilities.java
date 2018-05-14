package com.bewitchment.common.abilities;

import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.hotbar.HotbarAction;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ModAbilities {

	public static final HotbarAction BAT_SWARM = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "bat_swarm"), 2, 0);
	public static final HotbarAction DRAIN_BLOOD = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "drain_blood"), 1, 0);
	public static final HotbarAction HOWL = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "howl"), 1, 1);
	public static final HotbarAction WOLF_SHIFT = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "wolf_shift"), 3, 0) {
		@Override
		public int getIconIndexY(EntityPlayer player) {
			int yIndex = super.getIconIndexY(player);
			if (player.isSneaking()) {
				return yIndex;
			}
			return yIndex + 1;
		}
	};
	public static final HotbarAction NIGHT_VISION = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "night_vision"), 0, 0) {

		@Override
		public int getIconIndexY(EntityPlayer player) {
			int yIndex = super.getIconIndexY(player);
			if (player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()) {
				return yIndex;
			}
			return yIndex + 1;
		}

	};

	private ModAbilities() {
	}

	public static void dummyMethodToLoadClass() {
	}
}
