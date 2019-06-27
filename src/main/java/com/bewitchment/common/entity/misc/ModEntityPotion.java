package com.bewitchment.common.entity.misc;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

@SuppressWarnings({"unused", "NullableProblems"})
public class ModEntityPotion extends EntityPotion {
	public ModEntityPotion(World world) {
		super(world);
	}
	
	public ModEntityPotion(World world, EntityLivingBase thrower, ItemStack stack) {
		super(world, thrower, stack);
	}
	
	public ModEntityPotion(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if (!world.isRemote && result.typeOfHit != RayTraceResult.Type.MISS) {
			BlockPos pos;
			if (result.entityHit != null) pos = result.entityHit.getPosition();
			else pos = result.getBlockPos();
			for (PotionEffect effect : PotionUtils.getEffectsFromStack(getPotion())) if (effect.getPotion() instanceof ModPotion) ((ModPotion) effect.getPotion()).onImpact(world, pos, effect.getAmplifier());
		}
		super.onImpact(result);
	}
}