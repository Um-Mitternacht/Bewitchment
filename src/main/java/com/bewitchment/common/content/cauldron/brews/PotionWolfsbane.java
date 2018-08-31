package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.content.transformation.capability.ITransformationData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class PotionWolfsbane extends GenericBrewDamageVS {

	public PotionWolfsbane() {
		super("wolfsbane", 0xEFCC00);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		if (entity instanceof EntityWolf) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			ITransformationData data = entity.getCapability(CapabilityTransformationData.CAPABILITY, null);
			return data.getType() == DefaultTransformations.WEREWOLF;
		}
		return false;
	}

	@Override
	protected void applyExtraEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		if (amplifier > 2) {
			entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.WITHER, 40 * amplifier, 0));
		}
	}

}
