package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.potion.BrewMod;

import net.minecraft.entity.MoverType;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionCursedLeaping extends BrewMod {
	
	public PotionCursedLeaping() {
		super("cursed_leaping", true, 0x4F7942, false);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
	
	@SubscribeEvent
	public void onJump(LivingJumpEvent e) {
		PotionEffect pe = e.getEntityLiving().getActivePotionEffect(this);
		if (pe != null) {
			e.getEntityLiving().move(MoverType.SELF, 0, -0.1 * pe.getAmplifier(), 0);
		}
	}
	
}
