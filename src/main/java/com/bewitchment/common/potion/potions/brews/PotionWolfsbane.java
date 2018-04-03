package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.potion.BrewMod;
import com.bewitchment.common.transformation.ModTransformations;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class PotionWolfsbane extends BrewMod {
	
	public PotionWolfsbane() {
		super("wolfsbane", true, 0xEFCC00);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		if (isWolf(entityLivingBaseIn)) {
			entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), 5 + amplifier * 5);
			if (amplifier > 2) {
				entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200 * amplifier, 0));
			}
		}
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	private static boolean isWolf(EntityLivingBase entity) {
		if (entity instanceof EntityWolf) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			ITransformationData data = entity.getCapability(CapabilityTransformationData.CAPABILITY, null);
			return data.getType() == ModTransformations.WEREWOLF;
		}
		return false;
	}
	
}
