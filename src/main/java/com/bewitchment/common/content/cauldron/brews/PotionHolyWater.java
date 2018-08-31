package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.player.EntityPlayer;

public class PotionHolyWater extends GenericBrewDamageVS {

	public PotionHolyWater() {
		super("holy_water", 0x8DA399);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		if (entity instanceof EntityGhast) {
			return true;
		}
		if (entity instanceof EntityVex) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			ITransformation transformation = ((EntityPlayer) entity).getCapability(CapabilityTransformationData.CAPABILITY, null).getType();
			if (transformation == DefaultTransformations.VAMPIRE || transformation == DefaultTransformations.SPECTRE) {
				return true;
			}
		}
		return entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected float getDamage(int amplifier) {
		return 2 + amplifier * 1.5f;
	}

}
