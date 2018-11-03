package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PotionMagickalDampening extends BrewMod {

	//Todo: Someone tell me if this works right, I am still trying to figure out capabilities for these kinds of things
	//Magickal Boost will be a temporary booster potion (temporarily increasing the ME cap), whereas magickal boon will restore a specific amount of ME
	//There will also be brews that do the opposite
	public PotionMagickalDampening() {
		super("magickal_dampening", true, 0xFADA5E, false, 3000);
	}

	//Todo: Amount of ME lost should be dependant on the tier of the brew
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		IMagicPowerContainer playerMP = entity.getCapability(IMagicPowerContainer.CAPABILITY, null);
		playerMP.setMaxAmount(-100);
	}
}
