package com.bewitchment.common.entity.misc;

import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.world.World;

public class EntityElderBroom extends EntityBroom {
	public EntityElderBroom(World world) {
		super(world);
	}
	
	@Override
	protected float getSpeed() {
		return 2;
	}
	
	@Override
	protected float getMaxSpeed() {
		return 1.15f;
	}
}