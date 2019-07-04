package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings({"unused"})
public class DismountPlayer implements IMessage {
	public DismountPlayer() {
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
	}
	
	public static class Handler implements IMessageHandler<DismountPlayer, IMessage> {
		@Override
		public IMessage onMessage(DismountPlayer message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().player.dismountRidingEntity());
			return null;
		}
	}
}