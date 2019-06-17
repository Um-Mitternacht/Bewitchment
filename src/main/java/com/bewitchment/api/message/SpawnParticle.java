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
		type = EnumParticleTypes.getParticleFromId(byteBuf.getInt(0));
		pos = BlockPos.fromLong(byteBuf.getLong(1));
		speedX = byteBuf.getInt(2);
		speedY = byteBuf.getInt(3);
		speedZ = byteBuf.getInt(4);
	}
	
	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.setInt(0, type.getParticleID());
		byteBuf.setLong(1, pos.toLong());
		byteBuf.setInt(2, speedX);
		byteBuf.setInt(3, speedY);
		byteBuf.setInt(4, speedZ);
	}
	
	public class Handler implements IMessageHandler<SpawnParticle, IMessage> {
		@Override
		public IMessage onMessage(SpawnParticle message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) Minecraft.getMinecraft().world.spawnParticle(message.type, message.pos.getX(), message.pos.getY(), message.pos.getZ(), message.speedX, message.speedY, message.speedZ);
			return null;
		}
	}
}