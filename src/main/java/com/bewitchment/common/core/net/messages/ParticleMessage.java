package com.bewitchment.common.core.net.messages;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This class was created by Arekkuusu on 04/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ParticleMessage implements IMessage {

	private ParticleF particleF;
	private double x;
	private double y;
	private double z;
	private int amount;
	private double xSpeed;
	private double ySpeed;
	private double zSpeed;
	private int[] args;

	public ParticleMessage() {
	}

	public ParticleMessage(ParticleF particleF, double x, double y, double z, int amount, double xSpeed, double ySpeed, double zSpeed, int... args) {
		this.particleF = particleF;
		this.x = x;
		this.y = y;
		this.z = z;
		this.amount = amount;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
		this.args = args;
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		PacketBuffer buf = new PacketBuffer(byteBuf);
		particleF = buf.readEnumValue(ParticleF.class);
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		amount = buf.readInt();
		xSpeed = buf.readDouble();
		ySpeed = buf.readDouble();
		zSpeed = buf.readDouble();
		int argCount = buf.readInt();
		args = new int[argCount];
		for (int i = 0; i < argCount; i++) {
			args[i] = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		PacketBuffer buf = new PacketBuffer(byteBuf);
		buf.writeEnumValue(particleF);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(amount);
		buf.writeDouble(xSpeed);
		buf.writeDouble(ySpeed);
		buf.writeDouble(zSpeed);
		buf.writeInt(args.length);
		for (int arg : args) {
			buf.writeInt(arg);
		}
	}

	public static class ParticleMessageHandler implements IMessageHandler<ParticleMessage, IMessage> {

		@Override
		public IMessage onMessage(ParticleMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				for (int i = 0; i < message.amount; i++) {
					Bewitchment.proxy.spawnParticle(message.particleF, message.x, message.y, message.z, message.xSpeed, message.ySpeed, message.zSpeed, message.args);
				}
			});
			return null;
		}
	}
}
