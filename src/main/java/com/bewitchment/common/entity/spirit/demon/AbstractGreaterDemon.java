package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.IPledgeable;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractGreaterDemon extends ModEntityMob implements IPledgeable {
	public AbstractGreaterDemon(World world, ResourceLocation lootTableLocation) {
		super(world, lootTableLocation);
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	public boolean isNonBoss() {
		return false;
	}
	
}
