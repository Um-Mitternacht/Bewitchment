package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.content.transformation.capability.ITransformationData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
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
		if (entity instanceof EntityEnderman) {
			return true;
		}
		if (entity instanceof EntityEndermite) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			ITransformationData data = entity.getCapability(CapabilityTransformationData.CAPABILITY, null);
			return data.getType() == DefaultTransformations.VAMPIRE;
		}
		return false;
	}

}
