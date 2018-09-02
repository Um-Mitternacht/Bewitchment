package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.Bewitchment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrewVanilla implements IBrewEffect {

	private int duration = 0;

	public BrewVanilla(Potion potion) {
		for (PotionType p : PotionType.REGISTRY) {
			for (PotionEffect pe : p.getEffects()) {
				if (pe.getPotion() == potion && pe.getAmplifier() == 0) {
					if (pe.getDuration() < duration || duration == 0) {
						duration = pe.getDuration();
					}
				}
			}
		}

		if (duration == 0) {
			Bewitchment.logger.warn("Couldn't find the correct default duration for " + potion.getName());
			duration = 1800;
		}
	}

	public BrewVanilla(int duration) {
		this.duration = duration;
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
		return duration;
	}

	@Override
	public int getArrowDuration() {
		return getDefaultDuration() / 16;
	}

	@Override
	public int getLingeringDuration() {
		return getDefaultDuration() / 8;
	}

}
