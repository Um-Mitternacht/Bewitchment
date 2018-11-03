package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PotionPowerBoost extends BrewMod implements IMagicPowerExpander {

	public PotionPowerBoost() {
		super("power_boost", false, 0x66023C, false, 3000);
		this.setIconIndex(4, 2);
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase elb, AbstractAttributeMap attributeMapIn, int amplifier) {
		if (elb instanceof EntityPlayer) {
			BewitchmentAPI.getAPI().expandPlayerMP(this, (EntityPlayer) elb);
		}
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase elb, AbstractAttributeMap attributeMapIn, int amplifier) {
		if (elb instanceof EntityPlayer) {
			BewitchmentAPI.getAPI().removeMPExpansion(this, (EntityPlayer) elb);
		}
	}

	@Override
	public ResourceLocation getID() {
		return this.getRegistryName();
	}

	@Override
	public int getExtraAmount(EntityPlayer p) {
		if (p.getActivePotionEffect(this) != null) {
			return (p.getActivePotionEffect(this).getAmplifier() + 1) * 50;
		}
		return 0;
	}
}
