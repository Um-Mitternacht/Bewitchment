package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.transformation.ModTransformations;

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
			return data.getType() == ModTransformations.WEREWOLF;
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
