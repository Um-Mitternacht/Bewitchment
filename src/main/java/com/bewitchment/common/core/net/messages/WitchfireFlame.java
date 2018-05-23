package com.bewitchment.common.core.net.messages;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class WitchfireFlame extends SimpleMessage<WitchfireFlame> {

	public BlockPos posA, posB;
	public int color;

	public WitchfireFlame(BlockPos start, BlockPos end, int color) {
		this.posA = start;
		this.posB = end;
		this.color = color;
	}

	public WitchfireFlame() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		for (int i = 0; i < 100; i++) {
			Random r = Minecraft.getMinecraft().player.getRNG();
			Bewitchment.proxy.spawnParticle(ParticleF.COLORED_FLAME, posA.getX() + r.nextDouble(), posA.getY() + r.nextDouble() * 2, posA.getZ() + r.nextDouble(), 0.05 * r.nextGaussian(), 0.1 * r.nextGaussian(), 0.05 * r.nextGaussian(), color);
			Bewitchment.proxy.spawnParticle(ParticleF.COLORED_FLAME, posB.getX() + r.nextDouble(), posB.getY() + r.nextDouble() * 2, posB.getZ() + r.nextDouble(), 0.05 * r.nextGaussian(), 0.1 * r.nextGaussian(), 0.05 * r.nextGaussian(), color);
		}
		Minecraft.getMinecraft().world.playSound(posB, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.5f, 0.9f, false);
		Minecraft.getMinecraft().world.playSound(posA, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.5f, 0.9f, false);
		return null;
	}
}
