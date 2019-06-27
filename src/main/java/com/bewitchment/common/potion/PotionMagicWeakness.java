package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class PotionMagicWeakness extends ModPotion {
	public PotionMagicWeakness() {
		super("magic_weakness", true, 0x7f00c0);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onDamage(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getSource().isMagicDamage()) event.setAmount(event.getAmount() + 3 * (event.getEntityLiving().getActivePotionEffect(this).getAmplifier() + 1));
	}
}