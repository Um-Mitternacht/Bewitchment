package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 4/16/2020.
 */
public class EntityBafometyr extends ModEntityMob {
	public EntityBafometyr(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/bafometyr"));
		isImmuneToFire = true;
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 25;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
