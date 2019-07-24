package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused"})
public class SyncBroom implements IMessage {
	public int id;
	public double x, y, z;
	
	public SyncBroom() {
	}
	
	public SyncBroom(int id, double x, double y, double z) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		id = byteBuf.readInt();
		x = byteBuf.readDouble();
		y = byteBuf.readDouble();
		z = byteBuf.readDouble();
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeInt(id);
		byteBuf.writeDouble(x);
		byteBuf.writeDouble(y);
		byteBuf.writeDouble(z);
	}
	
	public static class Handler implements IMessageHandler<SyncBroom, IMessage> {
		@Override
		public IMessage onMessage(SyncBroom message, MessageContext ctx) {
			if (ctx.side.isClient()) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Entity entity = Minecraft.getMinecraft().player.world.getEntityByID(message.id);
					if (entity != null) 
					{
						entity.motionX = message.x;
						entity.motionY = message.y;
						entity.motionZ = message.z;
						//entity.setLocationAndAngles(message.x, message.y, message.z, entity.rotationYaw, entity.rotationPitch);
					}
				});
			}
			return null;
		}
	}
}