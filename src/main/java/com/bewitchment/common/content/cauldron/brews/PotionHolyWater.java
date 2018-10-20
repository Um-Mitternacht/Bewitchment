package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.core.helper.MobHelper;
import net.minecraft.entity.EntityLivingBase;

public class PotionHolyWater extends GenericBrewDamageVS {

	public PotionHolyWater() {
		super("holy_water", 0x8DA399);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return MobHelper.isSpirit(entity) || MobHelper.isDemon(entity) || MobHelper.isCorporealUndead(entity);
	}

	@Override
	protected float getDamage(int amplifier) {
		return 2 + amplifier * 1.5f;
	}

}
