package com.bewitchment.common.content.cauldron.brews;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class PotionBaneArthropods extends GenericBrewDamageVS {

	public PotionBaneArthropods() {
		super("bane_arthropods", 0x50C878);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD;
	}

}
