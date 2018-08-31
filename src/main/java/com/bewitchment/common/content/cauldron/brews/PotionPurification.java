package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class PotionPurification extends BrewMod {

	public PotionPurification() {
		super("purify", false, 0xFAEBD7, true, 0);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		ArrayList<PotionEffect> removalList = new ArrayList<>();
		entity.getActivePotionEffects().stream()
				.filter(pe -> pe.getPotion().isBadEffect())
				.filter(pe -> pe.getAmplifier() <= amplifier)
				.filter(pe -> !pe.getCurativeItems().isEmpty())
				.forEach(pe -> removalList.add(pe));
		removalList.forEach(pe -> entity.removePotionEffect(pe.getPotion()));
	}

}
