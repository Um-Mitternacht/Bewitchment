package com.bewitchment.common.entity.spirit.demon;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDemoness extends EntityDemon {
	public EntityDemoness(World world) {
		super(world);
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
}