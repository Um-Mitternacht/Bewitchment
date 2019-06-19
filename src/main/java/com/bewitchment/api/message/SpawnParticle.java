package com.bewitchment.api.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SpawnParticle implements IMessage {
	public EnumParticleTypes type;
	public BlockPos pos;
	public int speedX, speedY, speedZ;
	
	public SpawnParticle() {
	}
	
	public SpawnParticle(EnumParticleTypes type, BlockPos pos, int speedX, int speedY, int speedZ) {
		this.type = type;
		this.pos = pos;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
	}
	
	public SpawnParticle(EnumParticleTypes type, BlockPos pos) {
		this(type, pos, 0, 0, 0);
	}
	
	@Override
	public void fromBytes(ByteBuf byteBuf) {
		type = EnumParticleTypes.getParticleFromId(byteBuf.readInt());
		pos = BlockPos.fromLong(byteBuf.readLong());
		speedX = byteBuf.readInt();
		speedY = byteBuf.readInt();
		speedZ = byteBuf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeInt(type.getParticleID());
		byteBuf.writeLong(pos.toLong());
		byteBuf.writeInt(speedX);
		byteBuf.writeInt(speedY);
		byteBuf.writeInt(speedZ);
	}
	
	public static class Handler implements IMessageHandler<SpawnParticle, IMessage> {
		@Override
		public IMessage onMessage(SpawnParticle message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT)
				Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().world.spawnParticle(message.type, message.pos.getX(), message.pos.getY(), message.pos.getZ(), message.speedX, message.speedY, message.speedZ));
			return null;
		}
	}
}