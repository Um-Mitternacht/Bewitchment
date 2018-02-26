package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.helper.TransformationHelper;
import com.bewitchment.common.core.net.SimpleMessage;
import com.bewitchment.common.core.net.messages.RequestPlayerDataMessage.DataType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTransformationChangedMessage extends SimpleMessage<PlayerTransformationChangedMessage> {

	public int type, level;

	public PlayerTransformationChangedMessage() {
	}

	public PlayerTransformationChangedMessage(EntityPlayer player) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		type = data.getType().ordinal();
		level = data.getLevel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player != null) {
			TransformationHelper.setTypeAndLevel(player, EnumTransformationType.values()[type], level, true);
			if (EnumTransformationType.values()[type] == EnumTransformationType.VAMPIRE) {
				return new RequestPlayerDataMessage(DataType.VAMPIRE_BLOOD);
			}
		}
		return null;
	}

}
