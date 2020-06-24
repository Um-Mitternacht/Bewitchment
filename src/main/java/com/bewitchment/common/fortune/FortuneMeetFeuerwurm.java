package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.common.entity.spirit.demon.EntityFeuerwurm;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetFeuerwurm extends Fortune {
	public FortuneMeetFeuerwurm() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_feuerwurm"), true, (60), (60 * 3));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
		EntityFeuerwurm feuerwurm = new EntityFeuerwurm(player.world);
		if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(feuerwurm)) {
			feuerwurm.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			feuerwurm.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
			player.world.spawnEntity(feuerwurm);
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				feuerwurm.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				feuerwurm.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				feuerwurm.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				feuerwurm.addPotionEffect(new PotionEffect(ModPotions.magic_resistance, 900, 1));
			return true;
		}
		return false;
	}
}