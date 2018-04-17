package com.bewitchment.common.potion.potions.brews;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class PotionOutcastsShame extends GenericBrewDamageVS {

	public PotionOutcastsShame() {
		super("outcasts_shame", 0x8A3324);
	}
	
	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER;
	}
	
}
