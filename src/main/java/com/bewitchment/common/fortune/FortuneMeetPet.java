package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetPet extends Fortune {
	public FortuneMeetPet() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_pet"), false, (60 * 2), (60 * 60));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
		Entity entity = BewitchmentAPI.VALID_PETS.get(player.getRNG().nextInt(BewitchmentAPI.VALID_PETS.size())).newInstance(player.world);
		if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(entity)) {
			entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			if (entity instanceof EntityAnimal)
				((EntityAnimal) entity).onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
			if (entity instanceof EntityTameable) ((EntityTameable) entity).setTamedBy(player);
			if (entity instanceof AbstractHorse) ((AbstractHorse) entity).setTamedBy(player);
			player.world.spawnEntity(entity);
			return true;
		}
		return false;
	}
}