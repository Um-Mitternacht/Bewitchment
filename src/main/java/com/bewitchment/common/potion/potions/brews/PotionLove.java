package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.common.entity.EntityBrew;
import com.bewitchment.common.entity.EntityLingeringBrew;
import com.bewitchment.common.potion.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

public class PotionLove extends BrewMod {
	public PotionLove() {
		super("love", false, 0xff69b4, true, 0);
	}

	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		if (entityLivingBaseIn instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal) entityLivingBaseIn;
			if (animal.getGrowingAge() == 0 && !animal.isInLove()) {
				if (source instanceof EntityLingeringBrew) {
					EntityLingeringBrew brew = (EntityLingeringBrew) source;
					animal.setInLove((EntityPlayer) brew.getOwner());
				} else if (source instanceof EntityBrew) {
					EntityBrew brew = (EntityBrew) source;
					animal.setInLove((EntityPlayer) brew.getThrower());
				}
			}
		}
	}
}
