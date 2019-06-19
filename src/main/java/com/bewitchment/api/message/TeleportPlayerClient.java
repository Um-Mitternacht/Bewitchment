package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings({"unused"})
public class TeleportPlayerClient implements IMessage {
	public double x, y, z;
	
	public TeleportPlayerClient() {
	}
	
	public TeleportPlayerClient(double x, double y, double z) {
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		x = byteBuf.readDouble();
		y = byteBuf.readDouble();
		z = byteBuf.readDouble();
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeDouble(x);
		byteBuf.writeDouble(y);
		byteBuf.writeDouble(z);
	}
	
	public static class Handler implements IMessageHandler<TeleportPlayerClient, IMessage> {
		@Override
		public IMessage onMessage(TeleportPlayerClient message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					EntityPlayerSP player = Minecraft.getMinecraft().player;
					player.setLocationAndAngles(message.x, message.y, message.z, player.rotationYaw, player.rotationPitch);
				});
			}
			return null;
		}
	}
}