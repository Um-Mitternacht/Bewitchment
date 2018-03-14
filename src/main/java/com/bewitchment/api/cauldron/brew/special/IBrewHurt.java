package com.bewitchment.api.cauldron.brew.special;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * This class was created by Arekkuusu on 06/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface IBrewHurt {

	void onHurt(LivingHurtEvent event, DamageSource source, EntityLivingBase affected, int amplifier);
}
