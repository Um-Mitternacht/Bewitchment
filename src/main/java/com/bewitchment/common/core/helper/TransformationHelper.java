package com.bewitchment.common.core.helper;

import baubles.common.network.PacketHandler;
import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class TransformationHelper {

	private TransformationHelper() {
	}

	public static void setTypeAndLevel(EntityPlayer player, EnumTransformationType type, int level) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		data.setType(type);
		data.setLevel(level);
		HotbarAction.refreshActions(player, player.world);
		if (!player.world.isRemote)
			PacketHandler.INSTANCE.sendToDimension(new PlayerTransformationChangedMessage(player), player.world.provider.getDimension());
		MinecraftForge.EVENT_BUS.post(new TransformationModifiedEvent(player, type, level));
	}
}
