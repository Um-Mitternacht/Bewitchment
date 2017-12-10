package com.witchcraft.api.brew;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class BrewEffect {

	private IBrew brew;
	private int duration;
	private int amplifier;

	public BrewEffect(IBrew brew, int duration, int amplifier) {
		this.brew = brew;
		this.duration = duration;
		this.amplifier = amplifier;
	}

	public void start(World world, BlockPos pos, EntityLivingBase entity) {
		brew.onStart(world, pos, entity, amplifier);
		if (brew instanceof BrewAtributeModifier)
			((BrewAtributeModifier) brew).applyAttributeModifiers(entity.getAttributeMap(), amplifier);
	}

	public void update(World world, BlockPos pos, EntityLivingBase entity) {
		brew.apply(world, pos, entity, amplifier, duration--);
	}

	public void end(World world, BlockPos pos, EntityLivingBase entity) {
		brew.onFinish(world, pos, entity, amplifier);
		if (brew instanceof BrewAtributeModifier)
			((BrewAtributeModifier) brew).removeAttributeModifiers(entity.getAttributeMap(), amplifier);
	}

	public IBrew getBrew() {
		return brew;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}

	public boolean isInstant() {
		return brew.isInstant();
	}

	public void combine(BrewEffect other) {
		if (other.amplifier > this.amplifier) {
			this.amplifier = other.amplifier;
		}
		if (this.duration < other.duration) {
			this.duration = other.duration;
		}
	}

	public BrewEffect copy() {
		return new BrewEffect(brew, duration, amplifier);
	}
}
