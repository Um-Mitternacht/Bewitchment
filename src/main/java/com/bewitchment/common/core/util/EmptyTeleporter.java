package com.bewitchment.common.core.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class EmptyTeleporter implements ITeleporter {

	@Override
	public void placeEntity(World world, Entity entityIn, float yaw) {
		int i = MathHelper.floor(entityIn.posX);
		int j = MathHelper.floor(entityIn.posY);
		int k = MathHelper.floor(entityIn.posZ);

		entityIn.setLocationAndAngles(i, j, k, entityIn.rotationYaw, 0.0F);
		entityIn.motionX = 0.0D;
		entityIn.motionY = 0.0D;
		entityIn.motionZ = 0.0D;
	}

}
