package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class PotionGrace extends ModPotion {
	public PotionGrace() {
		super("grace", false, 0xc6c6c6);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onDamage(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getSource() == DamageSource.FALL) event.setAmount(event.getAmount() / (event.getEntityLiving().getActivePotionEffect(this).getAmplifier() + 2));
	}
}