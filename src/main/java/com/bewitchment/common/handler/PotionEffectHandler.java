package com.bewitchment.common.handler;

import com.bewitchment.registry.ModPotions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEffectHandler {

	@SubscribeEvent
	public void playerFireDamage(LivingHurtEvent event) {
		if (event.getSource().isFireDamage() && event.getEntityLiving().getActivePotionEffect(ModPotions.hellfire) != null) {
			int amp = event.getEntityLiving().getActivePotionEffect(ModPotions.hellfire).getAmplifier();
			float mod = 1.5f + amp * amp * 0.5f;
			event.setAmount(event.getAmount() * mod);
		}
	}
}
