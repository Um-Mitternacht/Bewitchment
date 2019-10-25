package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLeonard extends ModEntityMob {
	protected EntityLeonard(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/leonard"));
	}

	@Override
	protected boolean isValidLightLevel() {
		return false;
	}
}
