package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

@SuppressWarnings({"unused"})
public class PotionSleeping extends ModPotion {
	public PotionSleeping() {
		super("sleeping", true, 0xff007f);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (20 * 10) * (amplifier + 1), 3));
		living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, (20 * 10) * (amplifier + 1), 3));
		if (living.getHealth() <= (amplifier + 1) * 2) living.attackEntityFrom(DamageSource.MAGIC, Integer.MAX_VALUE);
	}
}