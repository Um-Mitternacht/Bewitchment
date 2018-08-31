package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionProjectileResistance extends BrewMod {

	public PotionProjectileResistance() {
		super("bulletproof", false, 0x2a3439, false, 3000);
		this.setIconIndex(2, 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		PotionEffect pe = event.getEntityLiving().getActivePotionEffect(this);
		if (pe != null && event.getSource().isProjectile() && event.getAmount() >= 2) {
			float newAmount = event.getAmount() - 0.5f * (pe.getAmplifier() + 1);
			if (newAmount < 2) {
				newAmount = 2;
			}
			event.setAmount(newAmount);
		}
	}

}
