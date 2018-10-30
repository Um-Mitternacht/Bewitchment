package com.bewitchment.common.potion.potions;

import com.bewitchment.common.potion.PotionMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;

public class PotionMesmerize extends PotionMod {

	public PotionMesmerize() {
		super("mesmerized", true, 0x7e03d4, false);
		this.setIconIndex(0, 2);
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
		entity.setRevengeTarget(null);
		entity.getCombatTracker().reset();
	}

}
