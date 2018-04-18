package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.transformation.ModTransformations;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;

public class PotionDemonsbane extends GenericBrewDamageVS {

	public PotionDemonsbane() {
		super("demons_bane", 0xFFF5EE);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON) {
			return true;
		}
		if (entity instanceof EntityBlaze) {
			return true;
		}
		if (entity instanceof EntityMagmaCube) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			ITransformationData data = entity.getCapability(CapabilityTransformationData.CAPABILITY, null);
			return data.getType() == ModTransformations.VAMPIRE;
		}
		return false;
	}

}
