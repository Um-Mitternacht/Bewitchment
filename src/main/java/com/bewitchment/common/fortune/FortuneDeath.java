package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class FortuneDeath extends Fortune {
	public FortuneDeath() {
		super(new ResourceLocation(Bewitchment.MODID, "death"), true, (60 * 20), (60 * 180));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 215, 1 + player.world.getDifficulty().ordinal(), false, false));
		return true;
	}
}