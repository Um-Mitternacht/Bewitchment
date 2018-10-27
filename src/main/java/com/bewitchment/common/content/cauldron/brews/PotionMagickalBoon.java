package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PotionMagickalBoon extends BrewMod {

	//Todo: Someone tell me if this works right, I am still trying to figure out capabilities for these kinds of things
	//Magickal Boost will be a temporary booster potion, whereas magickal boon will restore a specific amount of ME
	public PotionMagickalBoon() {
		super("magickal_boon", false, 0x7851A9, true, 0);
	}

	//Todo: Amount of ME restored should be dependant on the tier of the brew
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		IMagicPowerContainer playerMP = entity.getCapability(IMagicPowerContainer.CAPABILITY, null);
		playerMP.fill(100);
	}
}
