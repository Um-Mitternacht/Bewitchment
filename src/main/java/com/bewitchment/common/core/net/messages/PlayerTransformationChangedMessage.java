package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.capability.transformations.EnumTransformationType;
import com.bewitchment.api.capability.transformations.ITransformationData;
import com.bewitchment.api.capability.transformations.TransformationHelper;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.net.SimpleMessage;

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
		Minecraft.getMinecraft().addScheduledTask(() -> {
			TransformationHelper.setTypeAndLevel(Minecraft.getMinecraft().player, EnumTransformationType.values()[type], level, true);
		});
		return null;
	}

}
