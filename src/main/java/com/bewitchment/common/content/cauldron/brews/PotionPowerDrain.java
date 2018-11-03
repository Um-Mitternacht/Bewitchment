package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionPowerDrain extends BrewMod {

	public PotionPowerDrain() {
		super("power_drain", true, 0xE8AC41, false, 400);
		this.setIconIndex(2, 2);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 20 == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity instanceof EntityPlayer) {
			IMagicPowerContainer playerMP = entity.getCapability(IMagicPowerContainer.CAPABILITY, null);
			playerMP.drain(amplifier);
		}
	}

}
