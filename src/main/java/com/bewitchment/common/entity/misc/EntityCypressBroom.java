package com.bewitchment.common.entity.misc;

import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityCypressBroom extends EntityBroom {
	public EntityCypressBroom(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!world.isRemote && getControllingPassenger() instanceof EntityLivingBase && !((EntityLivingBase) getControllingPassenger()).isPotionActive(MobEffects.RESISTANCE))
			((EntityLivingBase) getControllingPassenger()).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (20 * 5), 1));
	}
	
	@Override
	protected float getSpeed() {
		return 1.5f;
	}
	
	@Override
	protected float getMaxSpeed() {
		return 1.05f;
	}
	
	@Override
	protected float getThrust() {
		return 0.125f;
	}
	
	@Override
	protected int getMagicCost() {
		return 1;
	}
}