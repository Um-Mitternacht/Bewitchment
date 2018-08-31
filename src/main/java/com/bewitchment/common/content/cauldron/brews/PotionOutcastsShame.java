package com.bewitchment.common.content.cauldron.brews;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityWitch;

public class PotionOutcastsShame extends GenericBrewDamageVS {

	public PotionOutcastsShame() {
		super("outcasts_shame", 0x8A3324);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		if (entity instanceof EntityWitch) {
			return true;
		}
		return entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER;
	}

	@Override
	protected float getDamage(int amplifier) {
		return 2 + amplifier * 1.5f;
	}

}
