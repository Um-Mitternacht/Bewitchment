package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DismountBroomMessage implements IMessage {

	@Override
	public void fromBytes(ByteBuf byteBuf) {

	}

	@Override
	public void toBytes(ByteBuf byteBuf) {

	}

	public static class Handler implements IMessageHandler<DismountBroomMessage, IMessage> {
		public IMessage onMessage(DismountBroomMessage message, MessageContext ctx) {
			if (ctx.side.isServer()) {
				ctx.getServerHandler().player.dismountRidingEntity();
			}
			if (ctx.side.isClient()) {
				Minecraft.getMinecraft().player.dismountRidingEntity();
			}
			return null;
		}
	}
}
