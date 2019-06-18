package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

//Todo: Find the old code for beeswarms so it can be used here. Also, a command is needed to make fortunes fire instantly. And clean this clusterfuck up.
public class FortuneCatsAndDogs extends Fortune {
	public FortuneCatsAndDogs() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_cats_and_dogs"), true);
	}
	
	@Override
	public boolean apply(EntityPlayer player) {
		if (player.getRNG().nextDouble() < 0.0001) {
			BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
			EntityWolf dog = new EntityWolf(player.world);
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(dog)) {
				dog.setPosition(pos.getX() + 0.5, pos.getY() + 255, pos.getZ() + 0.5);
				dog.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
				player.world.spawnEntity(dog);
				player.world.getWorldInfo().setCleanWeatherTime(0);
				player.world.getWorldInfo().setRainTime(2000);
				player.world.getWorldInfo().setThunderTime(3000);
				player.world.getWorldInfo().setRaining(true);
				player.world.getWorldInfo().setThundering(true);
				return true;
			}
			EntityOcelot cat = new EntityOcelot(player.world);
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(cat)) {
				cat.setPosition(pos.getX() + 0.5, pos.getY() + 255, pos.getZ() + 0.5);
				cat.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
				player.world.spawnEntity(cat);
				return true;
			}
		}
		return false;
	}
}