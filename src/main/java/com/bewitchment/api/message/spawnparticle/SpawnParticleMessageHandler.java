package com.bewitchment.api.message.spawnparticle;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SpawnParticleMessageHandler implements IMessageHandler<SpawnParticle, IMessage> {
	@Override
	public IMessage onMessage(SpawnParticle message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) Minecraft.getMinecraft().world.spawnParticle(message.type, message.pos.getX(), message.pos.getY(), message.pos.getZ(), message.speedX, message.speedY, message.speedZ);
		return null;
	}
}