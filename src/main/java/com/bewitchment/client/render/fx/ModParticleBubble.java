package com.bewitchment.client.render.fx;

import com.bewitchment.Bewitchment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("WeakerAccess")
@SideOnly(Side.CLIENT)
public class ModParticleBubble extends Particle {
	public static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "particle/bubble");

	public ModParticleBubble(World world, double x, double y, double z, double red, double green, double blue) {
		super(world, x, y, z);
		setSize(0.02F, 0.02F);
		particleMaxAge = (int) (8d / (Math.random() * 0.8 + 0.2));
		motionX *= 0.1;
		motionY *= 0.1;
		motionZ *= 0.1;
		float f = (float) (Math.random() * 0.4f + 0.6f);
		particleRed = (float) (((Math.random() * 0.2) + 0.8f) * red * f);
		particleGreen = (float) (((Math.random() * 0.2) + 0.8f) * green * f);
		particleBlue = (float) (((Math.random() * 0.2) + 0.8f) * blue * f);
		particleScale *= f / 2;
		setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TEX.toString()));
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY += 0.002;
		move(motionX, motionY, motionZ);
		motionX *= 0.85;
		motionY *= 0.85;
		motionZ *= 0.85;
		if (particleMaxAge-- <= 0 || world.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial() != Material.WATER)
			setExpired();
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@SuppressWarnings("NullableProblems")
	public static class Factory implements IParticleFactory {
		public Factory() {
		}

		@Override
		public Particle createParticle(int id, World world, double x, double y, double z, double colorX, double colorY, double colorZ, int... args) {
			return new ModParticleBubble(world, x, y, z, colorX, colorY, colorZ);
		}
	}
}