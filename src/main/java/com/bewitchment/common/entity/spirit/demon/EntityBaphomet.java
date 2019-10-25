package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityBaphomet extends ModEntityMob {
	protected EntityBaphomet(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/baphomet"));
		setSize(0.8f, 2.6f);
	}

	@Override
	protected boolean isValidLightLevel() {
		return false;
	}
}
