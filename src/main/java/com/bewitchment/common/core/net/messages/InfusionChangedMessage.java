package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.core.net.SimpleMessage;
import com.bewitchment.common.infusion.ModInfusions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class InfusionChangedMessage extends SimpleMessage<InfusionChangedMessage> {

	public ResourceLocation infusion;

	public InfusionChangedMessage(EntityPlayer p) {
		infusion = BewitchmentAPI.getAPI().getPlayerInfusion(p).getRegistryName();
	}

	public InfusionChangedMessage() {
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			BewitchmentAPI.getAPI().setPlayerInfusion(Minecraft.getMinecraft().player, ModInfusions.REGISTRY.getValue(infusion));
		});
		return null;
	}

}
