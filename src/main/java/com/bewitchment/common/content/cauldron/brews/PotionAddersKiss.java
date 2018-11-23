package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.entity.living.animals.EntityBlindworm;
import com.bewitchment.common.entity.living.animals.EntityLizard;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;

//Todo: Make snake venom return glass bottles after being used in the cauldron
public class PotionAddersKiss extends GenericBrewDamageVS {

	public PotionAddersKiss() {
		super("adders_kiss", 0xEDC9AF);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof EntityRabbit || entity instanceof EntityChicken || entity instanceof EntityBlindworm || entity instanceof EntityLizard || entity.getClass().getName().equals("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat");
	}

	@Override
	protected float getDamage(int amplifier) {
		return 4 + amplifier * 1.7f;
	}

}
