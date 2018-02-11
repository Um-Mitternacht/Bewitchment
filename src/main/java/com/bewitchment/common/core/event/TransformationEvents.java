package com.bewitchment.common.core.event;

import com.bewitchment.common.core.capability.CapabilityUtils;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.TransformationDataProvider;
import com.bewitchment.common.core.helper.TransformationHelper;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TransformationEvents {

	public static final ResourceLocation PLAYER_DATA = new ResourceLocation(LibMod.MOD_ID, "transformations");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(PLAYER_DATA, new TransformationDataProvider());
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {
		CapabilityUtils.copyDataOnPlayerRespawn(event, CapabilityTransformationData.CAPABILITY);
		TransformationHelper.syncTypeAndLevel(event.getEntityPlayer());
	}
}
