package com.bewitchment.common.content.actionbar;

import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModAbilities {

	public static final HotbarAction BAT_SWARM = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "bat_swarm"), 2, 0);
	public static final HotbarAction DRAIN_BLOOD = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "drain_blood"), 1, 0);
	public static final HotbarAction HOWL = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "howl"), 1, 1);
	public static final HotbarAction WOLF_SHIFT = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "wolf_shift"), 3, 0) {
		@Override
		@SideOnly(Side.CLIENT)
		public int getIconIndexY() {
			int yIndex = super.getIconIndexY();
			if (Minecraft.getMinecraft().player.isSneaking()) {
				return yIndex;
			}
			return yIndex + 1;
		}
	};
	public static final HotbarAction NIGHT_VISION = new HotbarAction(new ResourceLocation(LibMod.MOD_ID, "night_vision"), 0, 0) {

		@Override
		@SideOnly(Side.CLIENT)
		public int getIconIndexY() {
			int yIndex = super.getIconIndexY();
			if (Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()) {
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
