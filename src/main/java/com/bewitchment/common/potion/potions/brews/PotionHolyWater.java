package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.capability.transformations.ITransformation;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.transformation.ModTransformations;
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
			if (transformation == ModTransformations.VAMPIRE || transformation == ModTransformations.SPECTRE) {
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
