/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 * <p>
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package com.bewitchment.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public enum ParticleF {
	CAULDRON_BUBBLE(new ParticleBubble.Factory()),
	STEAM(new ParticleSteam.Factory()),
	BEE(new ParticleBee.Factory()),
	SPARK(new ParticleSpark.Factory()),
	FLAME(new ParticleOvenFlame.Factory()),
	COLORED_FLAME(new ParticleColoredflame.Factory()), BAT(new ParticleBat.Factory());

	private final IParticleF factory;

	ParticleF(IParticleF factory) {
		this.factory = factory;
	}

	public Particle newInstance(double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
		return factory.createParticle(Minecraft.getMinecraft().world, x, y, z, xSpeed, ySpeed, zSpeed, args);
	}
}
