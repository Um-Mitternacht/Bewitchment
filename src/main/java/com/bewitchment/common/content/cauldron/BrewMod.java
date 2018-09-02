package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.potion.PotionMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrewMod extends PotionMod implements IBrewEffect {

	protected int defaultDuration;

	public BrewMod(String name, boolean isBadEffectIn, int liquidColorIn, boolean isInstant, int defaultDuration) {
		super(name, isBadEffectIn, liquidColorIn, isInstant);
		this.defaultDuration = defaultDuration;
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
	}

	@Override
	public PotionEffect onApplyToEntity(EntityLivingBase entity, PotionEffect effect, IBrewModifierList modifiers, Entity thrower) {
		return effect;
	}

	@Override
	public boolean hasInWorldEffect() {
		return false;
	}

	@Override
	public int getDefaultDuration() {
		return defaultDuration;
	}

	@Override
	public int getArrowDuration() {
		return defaultDuration / 6;
	}

	@Override
	public int getLingeringDuration() {
		return defaultDuration / 5;
	}

}
