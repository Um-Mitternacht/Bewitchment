package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.core.helper.MobHelper;
import net.minecraft.entity.EntityLivingBase;

public class PotionDemonsbane extends GenericBrewDamageVS {

	public PotionDemonsbane() {
		super("demons_bane", 0xFFF5EE);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return MobHelper.isDemon(entity);
	}

}
