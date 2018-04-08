package com.bewitchment.common.potion.potions.brews;

import java.util.ArrayList;

import com.bewitchment.common.potion.BrewMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionCorruption extends BrewMod {
	
	public PotionCorruption() {
		super("corruption", true, 0x66FF00, true, 0);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		ArrayList<Potion> removalList = new ArrayList<>();
		entity.getActivePotionEffects().stream()
			.map(PotionEffect::getPotion)
			.filter(potion -> !potion.isBadEffect())
			.forEach(pot -> removalList.add(pot));
		removalList.forEach(pot -> entity.removePotionEffect(pot));
	}
}
