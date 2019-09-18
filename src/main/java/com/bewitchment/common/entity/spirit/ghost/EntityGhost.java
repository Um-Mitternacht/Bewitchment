package com.bewitchment.common.entity.spirit.ghost;

import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 9/18/2019.
 */
public class EntityGhost extends ModEntityMob {
	protected EntityGhost(World world, ResourceLocation lootTableLocation) {
		super(world, lootTableLocation);
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return false;
	}
}
