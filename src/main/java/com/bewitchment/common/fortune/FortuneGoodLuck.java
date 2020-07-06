package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class FortuneGoodLuck extends Fortune {
	public FortuneGoodLuck() {
		super(new ResourceLocation(Bewitchment.MODID, "good_luck"), false, (60 * 2), (60 * 10));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 666, 1 + player.world.getDifficulty().ordinal(), false, false));
		return true;
	}
}