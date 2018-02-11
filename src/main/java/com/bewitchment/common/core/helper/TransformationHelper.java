package com.bewitchment.common.core.helper;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;

import baubles.common.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;

public class TransformationHelper {
	
	private TransformationHelper() {
	}
	
	public static void setTypeAndLevel(EntityPlayer player, EnumTransformationType type, int level) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setType(type);
		data.setLevel(level);
		syncTypeAndLevel(player);
	}
	
	public static void syncTypeAndLevel(EntityPlayer player) {
		HotbarAction.refreshActions(player, player.world);
		PacketHandler.INSTANCE.sendToDimension(new PlayerTransformationChangedMessage(player), player.world.provider.getDimension());
	}
	
}
