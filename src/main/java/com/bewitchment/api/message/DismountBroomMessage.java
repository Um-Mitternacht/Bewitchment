package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
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
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.dismountRidingEntity();
			return null;
		}
	}
}
