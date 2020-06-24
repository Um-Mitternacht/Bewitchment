package com.bewitchment.api.message;

import com.bewitchment.client.render.fx.ModParticleBubble;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SpawnBubble implements IMessage {
	public double x, y, z, colorX, colorY, colorZ;

	public SpawnBubble() {
	}

	public SpawnBubble(double x, double y, double z, double colorX, double colorY, double colorZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.colorX = colorX;
		this.colorY = colorY;
		this.colorZ = colorZ;
	}

	public SpawnBubble(double x, double y, double z) {
		this(x, y, z, 0, 0, 0);
	}

	public SpawnBubble(BlockPos pos, double colorX, double colorY, double colorZ) {
		this(pos.getX(), pos.getY(), pos.getZ(), colorX, colorY, colorZ);
	}

	public SpawnBubble(BlockPos pos) {
		this(pos, 0, 0, 0);
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		x = byteBuf.readDouble();
		y = byteBuf.readDouble();
		z = byteBuf.readDouble();
		colorX = byteBuf.readDouble();
		colorY = byteBuf.readDouble();
		colorZ = byteBuf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeDouble(x);
		byteBuf.writeDouble(y);
		byteBuf.writeDouble(z);
		byteBuf.writeDouble(colorX);
		byteBuf.writeDouble(colorY);
		byteBuf.writeDouble(colorZ);
	}

	public static class Handler implements IMessageHandler<SpawnBubble, IMessage> {
		@Override
		public IMessage onMessage(SpawnBubble message, MessageContext ctx) {
			if (ctx.side.isClient())
				Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().effectRenderer.addEffect(new ModParticleBubble.Factory().createParticle(0, Minecraft.getMinecraft().player.world, message.x, message.y, message.z, message.colorX, message.colorY, message.colorZ)));
			return null;
		}
	}
}