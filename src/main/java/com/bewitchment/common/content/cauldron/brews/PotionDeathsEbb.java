package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionDeathsEbb extends BrewMod {

	public PotionDeathsEbb() {
		super("deaths_ebb", true, 0x6c7c59, true, 0);
		this.setIconIndex(5, 1);
	}

	//Fixme: Why isn't this working?
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		World world = entity.world;
		if (!world.isRemote) {
			BlockPos pos = entity.getPosition();
			EntityZombie zombie = new EntityZombie(world);
			zombie.setPosition(pos.getX(), pos.getY(), pos.getZ());
			world.spawnEntity(zombie);
		}
	}
}
