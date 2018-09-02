package com.bewitchment.common.content.incantation;

import com.bewitchment.api.incantation.IIncantation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/**
 * This class was created by Arekkuusu on 19/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class IncantationFisheye implements IIncantation {

	//Todo: Make this only affect vision.
	@SuppressWarnings("ConstantConditions")
	@Override
	public void cast(EntityPlayer sender, String[] args) {
		sender.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 275, 0));
		sender.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 275, 0));
	}

	@Override
	public int getCost() {
		return 800;
	}
}
