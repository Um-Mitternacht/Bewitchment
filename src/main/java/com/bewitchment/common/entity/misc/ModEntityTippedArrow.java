package com.bewitchment.common.entity.misc;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Set;

@SuppressWarnings({"unused"})
public class ModEntityTippedArrow extends EntityTippedArrow {
	public ModEntityTippedArrow(World world) {
		super(world);
	}
	
	public ModEntityTippedArrow(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	public ModEntityTippedArrow(World world, EntityLivingBase shooter) {
		super(world, shooter);
	}
	
	@Override
	protected void onHit(RayTraceResult result) {
		boolean flag = false;
		Set<PotionEffect> effects = ObfuscationReflectionHelper.getPrivateValue(EntityTippedArrow.class, this, "customPotionEffects", "field_184561_h");
		if (!world.isRemote && result.typeOfHit != RayTraceResult.Type.MISS) {
			BlockPos pos;
			if (result.entityHit != null) pos = result.entityHit.getPosition();
			else pos = result.getBlockPos();
			for (PotionEffect effect : effects) if (effect.getPotion() instanceof ModPotion && ((ModPotion) effect.getPotion()).onImpact(world, pos, effect.getAmplifier())) flag = true;
		}
		super.onHit(result);
		if (flag) effects.clear();
	}
}