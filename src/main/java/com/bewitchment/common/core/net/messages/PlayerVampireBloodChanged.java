package com.bewitchment.common.core.net.messages;

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

import java.util.UUID;

public class PlayerVampireBloodChanged extends SimpleMessage<PlayerVampireBloodChanged> {

	public UUID id;
	public int amount;

	public PlayerVampireBloodChanged() {
	}

	public PlayerVampireBloodChanged(EntityPlayer player) {
		id = player.getUniqueID();
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		amount = data.getBlood();
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player != null) {
			TransformationHelper.setVampireBlood(player, amount);
		}
		return null;
	}

}
