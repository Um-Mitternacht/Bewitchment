package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetPet extends Fortune {
	public FortuneMeetPet() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_pet"));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		if (player.getRNG().nextDouble() < 0.0001) {
			BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
			EntityAnimal entity;
			int rand = player.getRNG().nextInt(4);
			if (rand == 0) entity = new EntityOcelot(player.world);
			else if (rand == 1) entity = new EntityWolf(player.world);
			else if (rand == 2)
				entity = player.getRNG().nextBoolean() ? new EntityHorse(player.world) : new EntityDonkey(player.world);
			else if (rand == 3) entity = new EntityLlama(player.world);
			else entity = new EntityParrot(player.world);
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(entity)) {
				entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				entity.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
				if (entity instanceof EntityTameable) ((EntityTameable) entity).setTamedBy(player);
				if (entity instanceof AbstractHorse) ((AbstractHorse) entity).setTamedBy(player);
				player.world.spawnEntity(entity);
				return true;
			}
		}
		return false;
	}
}