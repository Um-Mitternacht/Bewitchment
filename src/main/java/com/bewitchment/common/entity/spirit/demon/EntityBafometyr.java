package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 4/16/2020.
 */
public class EntityBafometyr extends ModEntityMob {
	public EntityBafometyr(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/bafometyr"));
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
