package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionCursedLeaping extends BrewMod {

	public PotionCursedLeaping() {
		super("cursed_leaping", true, 0x4F7942, false, 2400);
		MinecraftForge.EVENT_BUS.register(this);
		this.setIconIndex(3, 0);
	}

	@SubscribeEvent
	public void onJump(LivingJumpEvent e) {
		PotionEffect pe = e.getEntityLiving().getActivePotionEffect(this);
		if (pe != null) {
			e.getEntityLiving().motionY -= 0.1 * (1 + pe.getAmplifier());
		}
	}

}
