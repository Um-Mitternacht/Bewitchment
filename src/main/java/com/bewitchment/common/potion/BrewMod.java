package com.bewitchment.common.potion;

import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifierList;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrewMod extends PotionMod implements IBrewEffect {
	
	public BrewMod(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(name, isBadEffectIn, liquidColorIn);
	}
	
	@Override
	public void applyInWorld(World world, BlockPos pos, IBrewModifierList modifiers, EntityLivingBase thrower) {
	}
	
	@Override
	public PotionEffect onApplyToEntity(EntityLivingBase entity, PotionEffect effect, IBrewModifierList modifiers, EntityLivingBase thrower) {
		return effect;
	}
	
	@Override
	public void onEffectCloudSpawned(EntityAreaEffectCloud cloud, IBrewModifierList modifiers) {
	}
	
	@Override
	public boolean hasInWorldEffect() {
		return false;
	}
	
}
