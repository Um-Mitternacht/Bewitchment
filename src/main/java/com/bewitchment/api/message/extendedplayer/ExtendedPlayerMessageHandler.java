package com.bewitchment.api.message.extendedplayer;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings("ConstantConditions")
public class ExtendedPlayerMessageHandler implements IMessageHandler<SyncExtendedPlayer, IMessage> {
	@Override
	public IMessage onMessage(SyncExtendedPlayer message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().player.getCapability(ExtendedPlayer.CAPABILITY, null).deserializeNBT(message.cap.serializeNBT()));
		return null;
	}
}