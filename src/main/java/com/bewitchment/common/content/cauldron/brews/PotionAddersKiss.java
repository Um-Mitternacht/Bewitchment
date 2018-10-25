package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.core.helper.MobHelper;
import net.minecraft.entity.EntityLivingBase;

//Todo: Make snake venom return glass bottles after being used in the cauldron
public class PotionAddersKiss extends GenericBrewDamageVS {

	public PotionAddersKiss() {
		super("adders_kiss", 0xEDC9AF);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return MobHelper.isSnakeFodder(entity) || MobHelper.isOwlFodder(entity);
	}

	@Override
	protected float getDamage(int amplifier) {
		return 4 + amplifier * 1.7f;
	}

}
