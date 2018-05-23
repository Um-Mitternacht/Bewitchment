package com.bewitchment.client.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticleColoredflame extends ParticleFlame {

	private int color;

	protected ParticleColoredflame(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int color) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.color = color;
	}

	private static float fv(int col, int shift) {
		return ((col >> shift) & 0xFF) / 255f;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		this.setRBGColorF(fv(color, 16), fv(color, 8), fv(color, 0));
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@SideOnly(Side.CLIENT)
	static class Factory implements IParticleF {
		@Override
		public Particle createParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... args) {
			if (args.length == 0) {
				args = new int[]{0xFFFFFF};
			}
			return new ParticleColoredflame(worldIn, xCoordIn, yCoordIn, zCoordIn, zSpeedIn, zSpeedIn, zSpeedIn, args[0]);
		}
	}

}
