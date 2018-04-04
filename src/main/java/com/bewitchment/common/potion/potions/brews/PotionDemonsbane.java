package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.potion.BrewMod;
import com.bewitchment.common.transformation.ModTransformations;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class PotionDemonsbane extends BrewMod {
	
	public PotionDemonsbane() {
		super("demons_bane", true, 0xFFF5EE, true);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		if (isDemon(entityLivingBaseIn)) {
			entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), 5 + amplifier * 5);
		}
	}
	
	private static boolean isDemon(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			ITransformationData data = entity.getCapability(CapabilityTransformationData.CAPABILITY, null);
			return data.getType() == ModTransformations.VAMPIRE;
		}
		return false;
	}
	
}
