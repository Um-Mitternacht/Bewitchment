package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PotionMagickalDrain extends BrewMod {

	//Todo: Someone tell me if this works right, I am still trying to figure out capabilities for these kinds of things
	//Magickal Boost will be a temporary booster potion (temporarily increasing the ME cap), whereas magickal boon will restore a specific amount of ME
	//There will also be brews that do the opposite
	public PotionMagickalDrain() {
		super("magickal_drain", true, 0xE8AC41, true, 0);
	}

	//Todo: Amount of ME drain should be dependant on the tier of the brew
	//Todo: Allow this to also target other mana-type systems
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		IMagicPowerContainer playerMP = entity.getCapability(IMagicPowerContainer.CAPABILITY, null);
		playerMP.drain(100);
	}
}
