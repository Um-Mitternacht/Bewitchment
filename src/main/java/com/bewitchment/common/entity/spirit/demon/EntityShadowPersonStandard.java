package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 10/21/2019.
 */
public class EntityShadowPersonStandard extends ModEntityMob {
	
	
	public EntityShadowPersonStandard(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/shadow_person"));
		setSize(1, 0.85f);
		isImmuneToFire = true;
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
