package com.bewitchment.common.handler;

import com.bewitchment.api.BewitchmentAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ArmorHandler {
	@SubscribeEvent
	public void reducePotionDamage(LivingDamageEvent event) {
		if (!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getSource().getDamageType().equals("indirectMagic") && BewitchmentAPI.hasAlchemistGear(player)) {
				event.setAmount(event.getAmount() * 0.5f);
			}
		}
	}
	
	//	@SubscribeEvent
	//	public void reduceNegativeEffectLevel(PotionEvent.PotionAddedEvent event) {
	//		if (!event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer) {
	//			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
	//			if (BewitchmentAPI.hasAlchemistGear(player)) {
	//				PotionEffect effect = event.getPotionEffect();
	//				if (event.getPotionEffect().getPotion().isBadEffect() && effect.getAmplifier() > 0) {
	//					PotionEffect newEffect = new PotionEffect(effect.getPotion(), effect.getDuration(), 0);
	//					player.addPotionEffect(newEffect);
	//				}
	//			}
	//		}
	//	}
}
