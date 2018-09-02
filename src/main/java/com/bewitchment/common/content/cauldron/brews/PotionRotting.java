package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;

public class PotionRotting extends BrewMod {

	public PotionRotting() {
		super("rotting", true, 0x4B5320, false, 60 * 20);
		this.setIconIndex(1, 1);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
			entity.attackEntityFrom(DamageSource.MAGIC, 0.5f + amplifier / 2.5f);
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0;
	}

}
