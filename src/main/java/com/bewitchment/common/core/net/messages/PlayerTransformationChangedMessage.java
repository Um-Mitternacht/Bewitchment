package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.content.transformation.ModTransformations;
import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTransformationChangedMessage extends SimpleMessage<PlayerTransformationChangedMessage> {

	public int level;
	public String type;

	public PlayerTransformationChangedMessage() {
	}

	public PlayerTransformationChangedMessage(EntityPlayer player) {
		CapabilityTransformation data = player.getCapability(CapabilityTransformation.CAPABILITY, null);
		type = data.getType().getRegistryName().toString();
		level = data.getLevel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			BewitchmentAPI.getAPI().setTypeAndLevel(Minecraft.getMinecraft().player, ModTransformations.REGISTRY.getValue(new ResourceLocation(type)), level, true);
		});
		return null;
	}

}
