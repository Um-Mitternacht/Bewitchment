package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetBlaze extends Fortune {
	public FortuneMeetBlaze() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_blaze"), true, (60), (60 * 3));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
		EntityBlaze blaze = new EntityBlaze(player.world);
		if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(blaze)) {
			blaze.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			blaze.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
			player.world.spawnEntity(blaze);
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				blaze.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				blaze.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				blaze.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				blaze.addPotionEffect(new PotionEffect(ModPotions.magic_resistance, 900, 1));
			return true;
		}
		return false;
	}
}