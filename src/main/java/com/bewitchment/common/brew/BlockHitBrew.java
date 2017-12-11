package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.brew.IBrewEntityImpact;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public abstract class BlockHitBrew implements IBrew, IBrewEntityImpact {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO-OP
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		//NO-OP
	}

	@Override
	public void impact(RayTraceResult trace, World world, int amplifier) {
		if (trace.typeOfHit == RayTraceResult.Type.ENTITY) {
			safeImpact(trace.entityHit.getPosition(), null, world, amplifier);
		} else if (trace.typeOfHit == RayTraceResult.Type.BLOCK) {
			safeImpact(trace.getBlockPos(), trace.sideHit, world, amplifier);
		}
	}

	abstract void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier);
}
