package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetDireWolf extends Fortune {
	public FortuneMeetDireWolf() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_dire_wolf"), true, (60 * 5), (60 * 20));
	}
	
	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
		EntityWolf wolf = new EntityWolf(player.world);
		if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(wolf)) {
			wolf.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			wolf.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
			wolf.setAttackTarget(player);
			player.world.spawnEntity(wolf);
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal()) wolf.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal()) wolf.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal()) wolf.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
			return true;
		}
		return false;
	}
}