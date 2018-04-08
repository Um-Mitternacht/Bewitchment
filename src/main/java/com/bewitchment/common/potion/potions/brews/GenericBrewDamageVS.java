package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.potion.BrewMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public abstract class GenericBrewDamageVS extends BrewMod {
	
	public GenericBrewDamageVS(String name, int liquidColorIn) {
		super(name, true, liquidColorIn, true, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		if (shouldAffect(entityLivingBaseIn)) {
			entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), 4 + amplifier * 3);
			applyExtraEffect(entityLivingBaseIn, amplifier);
		}
	}
	
	protected abstract boolean shouldAffect(EntityLivingBase entity);
	
	protected void applyExtraEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
	}
	
}
