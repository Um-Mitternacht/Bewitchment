package com.bewitchment.common.potion.potions.brews;

import java.util.ArrayList;

import com.bewitchment.common.potion.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class PotionAbsence extends BrewMod {

	public PotionAbsence() {
		super("absence", true, 0x808080, true, 0);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		ArrayList<PotionEffect> removalList = new ArrayList<>();
		entity.getActivePotionEffects().stream()
			.filter(pe -> pe.getAmplifier()<= amplifier)
			.forEach(pe -> removalList.add(pe));
		removalList.forEach(pe -> entity.removePotionEffect(pe.getPotion()));
	}

}
