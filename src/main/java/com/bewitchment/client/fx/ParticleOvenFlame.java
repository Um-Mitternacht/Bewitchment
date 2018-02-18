package com.bewitchment.client.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 10/27/2017.
 */
public class ParticleOvenFlame extends Particle {
	protected ParticleOvenFlame(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}

	@SideOnly(Side.CLIENT)
	static class Factory implements IParticleF {
		@Override
		public Particle createParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... args) {
			return new ParticleOvenFlame(worldIn, xCoordIn, yCoordIn, zCoordIn);
		}
	}
}
