package com.bewitchment.common.potion.potions;

import com.bewitchment.common.content.cauldron.brews.GenericBrewDamageVS;
import com.bewitchment.common.core.helper.MobHelper;
import net.minecraft.entity.EntityLivingBase;

public class PotionGarlicked extends GenericBrewDamageVS {

	public PotionGarlicked() {
		super("garlicked", 0x8DA399);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return MobHelper.isCorporealUndead(entity);
	}

	@Override
	protected float getDamage(int amplifier) {
		return 4 + amplifier * 1.5f;
	}

}
