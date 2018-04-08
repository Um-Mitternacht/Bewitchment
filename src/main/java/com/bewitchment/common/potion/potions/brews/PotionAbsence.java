package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.potion.BrewMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PotionAbsence extends BrewMod {
	
	public PotionAbsence() {
		super("absence", true, 0x808080, true, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		entityLivingBaseIn.clearActivePotions();
	}
	
}
