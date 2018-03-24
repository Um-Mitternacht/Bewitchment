package com.bewitchment.common.divination.fortunes;

import com.bewitchment.common.divination.Fortune;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetIllusioner extends Fortune {

	public FortuneMeetIllusioner(int weight, String name, String modid) {
		super(weight, name, modid);
	}

	@Override
	public boolean canBeUsedFor(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(EntityPlayer player) {
		return player.getRNG().nextDouble() < 0.0001;
	}

	@Override
	public boolean apply(EntityPlayer player) {
		for (int i = 0; i < 10; i++) {
			//FIXME: Illusioners won't spawn.
			BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
			EntityIllusionIllager illusioner = new EntityIllusionIllager(player.world) {
			};
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(illusioner)) {
				illusioner.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				illusioner.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
				player.world.spawnEntity(illusioner);
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 900, 1));
				if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
					illusioner.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 900, 1));
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
