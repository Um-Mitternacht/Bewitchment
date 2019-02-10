package com.bewitchment.common.content.crystalBall.fortunes;

import com.bewitchment.common.content.crystalBall.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

/**
 * Created by Joseph on 1/16/2018.
 */

public class FortuneIllness extends Fortune {

	public FortuneIllness(int weight, String name, String modid) {
		super(weight, name, modid);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean canBeUsedFor(@Nonnull EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(@Nonnull EntityPlayer player) {
		return player.getRNG().nextDouble() < 0.0005d;
	}

	@Override
	public boolean apply(@Nonnull EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 215, 1 + player.world.getDifficulty().ordinal(), false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 215, 1 + player.world.getDifficulty().ordinal(), false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 215, 1 + player.world.getDifficulty().ordinal(), false, false));
		return true;
	}

	@Override
	public boolean isNegative() {
		return true;
	}
}
