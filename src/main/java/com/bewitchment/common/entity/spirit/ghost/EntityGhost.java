package com.bewitchment.common.entity.spirit.ghost;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 9/18/2019.
 */
public class EntityGhost extends ModEntityMob {
	protected EntityGhost(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/ghost"));
		isImmuneToFire = true;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.SPIRIT;
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return !world.isDaytime();
	}
}
