package com.bewitchment.common.entity.misc;

import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.world.World;

public class EntityCypressBroom extends EntityBroom {
	public EntityCypressBroom(World world) {
		super(world);
	}
	
	@Override
	protected float getSpeed() {
		return 1;
	}
	
	@Override
	protected float getMaxSpeed() {
		return 1;
	}
}