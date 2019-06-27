package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.IProjectile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class PotionDeflection extends ModPotion {
	public PotionDeflection() {
		super("deflection", false, 0xc000ff);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onAttacked(LivingAttackEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getSource().getImmediateSource() instanceof IProjectile) {
			int amplifier = event.getEntityLiving().getActivePotionEffect(this).getAmplifier();
			event.getSource().getImmediateSource().motionX *= 4 * amplifier;
			event.getSource().getImmediateSource().motionY *= 4 * amplifier;
			event.getSource().getImmediateSource().motionZ *= 4 * amplifier;
			event.setCanceled(true);
		}
	}
}