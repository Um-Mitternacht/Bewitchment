package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class PlayerTransformationChangedMessage extends SimpleMessage<PlayerTransformationChangedMessage> {

	public UUID id;
	public int type, level;

	public PlayerTransformationChangedMessage() {
	}

	public PlayerTransformationChangedMessage(EntityPlayer player) {
		id = player.getUniqueID();
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		type = data.getType().ordinal();
		level = data.getLevel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(id);
		if (player != null) {
			ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
			data.setType(EnumTransformationType.values()[type]);
			data.setLevel(level);
			HotbarAction.refreshActions(player, player.world);
		}
		return null;
	}

}
