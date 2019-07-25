package com.bewitchment.api.message;

import com.bewitchment.api.registry.entity.EntityBroom;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused"})
public class SyncBroom implements IMessage {
	public int id;
	public double x, y, z;
	
	public SyncBroom() {
	}
	
	public SyncBroom(EntityBroom broom) {
		this.id = broom.getEntityId();
		this.x = broom.posX;
		this.y = broom.posY;
		this.z = broom.posZ;
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
					EntityBroom entity = (EntityBroom) Minecraft.getMinecraft().player.world.getEntityByID(message.id);
					if (entity != null) entity.setLocationAndAngles(message.x, message.y, message.z, entity.rotationYaw, entity.rotationPitch);
				});
			}
			return null;
		}
	}
}