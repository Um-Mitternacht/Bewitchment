package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Created by Joseph on 3/30/2020.
 */
public class EntityCambion extends ModEntityMob {
	protected EntityCambion(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/cambion"));
	}
	
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
	}
	
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.EVOCATION_ILLAGER_DEATH;
	}
	
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
