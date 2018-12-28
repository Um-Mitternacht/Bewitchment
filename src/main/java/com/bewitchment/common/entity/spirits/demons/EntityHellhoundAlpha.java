package com.bewitchment.common.entity.spirits.demons;

import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;

/**
 * Created by Joseph on 12/28/2018.
 */
public class EntityHellhoundAlpha extends EntityHellhound {
	public EntityHellhoundAlpha(World worldIn) {
		super(worldIn);
		setSize(1.6F, 1.6F);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
	}
}
