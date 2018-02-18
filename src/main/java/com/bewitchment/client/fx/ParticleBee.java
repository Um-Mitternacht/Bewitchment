package com.bewitchment.client.fx;

import com.bewitchment.client.ResourceLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 08/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SideOnly(Side.CLIENT)
class ParticleBee extends Particle {

	private final double x;
	private final double y;
	private final double z;

	private ParticleBee(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = 0.2F;
		this.particleMaxAge = 200;
		this.x = posXIn;
		this.y = posYIn;
		this.z = posZIn;

		final TextureAtlasSprite atlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(ResourceLocations.BEE.toString());
		setParticleTexture(atlasSprite);
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY *= 0.6000000238418579D;
		this.move(this.motionX, this.motionY, this.motionZ);

		if (this.particleMaxAge-- > 0) {
			final double d0 = x + 0.5D - this.posX;
			final double d1 = y + 0.1D - this.posY;
			final double d2 = z + 0.5D - this.posZ;
			if (rand.nextBoolean()) {
				this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
				this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
				this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
			} else {
				this.motionX -= (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
				this.motionY -= (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
				this.motionZ -= (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
			}
		} else {
			setExpired();
		}
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	static class Factory implements IParticleF {
		@Override
		public Particle createParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... args) {
			return new ParticleBee(worldIn, xCoordIn, yCoordIn, zCoordIn);
		}
	}
}
