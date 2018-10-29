package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

public class SmokeSpawn extends SimpleMessage<SmokeSpawn> {
	public double x, y, z;

	public SmokeSpawn(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public SmokeSpawn() {
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			Random r = new Random();
			for (int i = 0; i < 5; i++) {
				Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.CLOUD, x + r.nextGaussian() * 0.2, y + r.nextDouble() * 0.2, z + r.nextGaussian() * 0.2, 0, 0.1, 0);
			}
		});
		return null;
	}

}
