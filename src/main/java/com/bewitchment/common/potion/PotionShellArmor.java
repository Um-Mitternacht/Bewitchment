package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class PotionShellArmor extends ModPotion {
	public PotionShellArmor() {
		super("shell_armor", false, 0x00c040);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onDamage(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getSource().getTrueSource() instanceof EntityLivingBase)
			event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), 3 * (event.getEntityLiving().getActivePotionEffect(this).getAmplifier() + 1));
	}
}