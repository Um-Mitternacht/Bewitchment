package com.bewitchment.common.core.net.messages;

import java.util.UUID;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.helper.TransformationHelper;
import com.bewitchment.common.core.net.SimpleMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
			TransformationHelper.setTypeAndLevel(player, EnumTransformationType.values()[type], level);
		}
		return null;
	}

}
