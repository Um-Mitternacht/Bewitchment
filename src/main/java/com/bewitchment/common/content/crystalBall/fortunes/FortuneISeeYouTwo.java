package com.bewitchment.common.content.crystalBall.fortunes;

import com.bewitchment.common.content.crystalBall.Fortune;
import com.bewitchment.common.entity.spirits.demons.EntityUran;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

public class FortuneISeeYouTwo extends Fortune {

	public FortuneISeeYouTwo(int weight, String name, String modid) {
		super(weight, name, modid);
	}

	@Override
	public boolean canBeUsedFor(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean apply(EntityPlayer player) {
		for (int i = 0; i < 10; i++) {
			BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
			EntityUran uran = new EntityUran(player.world);
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(uran)) {
				uran.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				uran.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
				player.world.spawnEntity(uran);
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					uran.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					uran.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					uran.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					uran.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 900, 1));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isNegative() {
		return true;
	}

}
