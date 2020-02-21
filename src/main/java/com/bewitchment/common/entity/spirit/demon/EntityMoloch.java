package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.IPledgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 2/21/2020.
 */
public class EntityMoloch extends AbstractGreaterDemon implements IPledgeable {
	public EntityMoloch(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/moloch"));
	}
	
	@Override
	public String getPledgeName() {
		return "moloch";
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return false;
	}
}
