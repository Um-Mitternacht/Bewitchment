package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class CursePalorPestilence extends Curse {
	public CursePalorPestilence() {
		super(new ResourceLocation(Bewitchment.MODID, "palor_pestilence"), Arrays.asList(Util.get(ModObjects.aconitum), Util.get(ModObjects.hellebore), Util.get(ModObjects.snake_venom), Util.get(ModObjects.belladonna), Util.get(ModObjects.taglock)), false, CurseCondition.EXIST, 0.0004);
	}

	@Override
	public boolean doCurse(EntityPlayer target) {
		int level = this.getLevel();
		if (!target.world.isRemote) {
			switch (target.getRNG().nextInt(2)) {
				case 0:
					target.addPotionEffect(new PotionEffect(MobEffects.WITHER, (level + 1) * 300));
					break;
				case 1:
					target.addPotionEffect(new PotionEffect(MobEffects.POISON, (level + 1) * 300));
			}
			return true;
		}
		return false;
	}
}
