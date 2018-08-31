package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionPathOfTheDeep extends BrewMod {

	public PotionPathOfTheDeep() {
		super("path_of_the_deep", false, 0x59d2ff, false, 3 * 20 * 60);
		setIconIndex(0, 1);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity.isInWater() && (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isFlying)) {
			if (entity.moveForward > 0F) {
				entity.moveRelative(0F, 0, 0.02F * amplifier, 0.85F);
			}
		}
	}

}
