package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 9/7/2019.
 */
public class EntityDruden extends ModEntityMob {
	
	public EntityDruden(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/drude"));
		setSize(1.425f, 4.0f);
		isImmuneToFire = false;
		experienceValue = 20;
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return false;
	}
}