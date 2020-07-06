package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.common.entity.spirit.demon.EntityCambion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetCambion extends Fortune {
	public FortuneMeetCambion() {
		super(new ResourceLocation(Bewitchment.MODID, "meet_cambion"), true, (60 * 5), (60 * 25));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
		EntityCambion cambion = new EntityCambion(player.world);
		if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(cambion)) {
			cambion.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			cambion.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
			player.world.spawnEntity(cambion);
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				cambion.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				cambion.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				cambion.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 1));
			if (player.getRNG().nextInt(10) < player.world.getDifficulty().ordinal())
				cambion.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 900, 1));
			return true;
		}
		return false;
	}
}