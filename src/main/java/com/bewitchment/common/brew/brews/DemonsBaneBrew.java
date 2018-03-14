package com.bewitchment.common.brew.brews;

import com.bewitchment.api.cauldron.brew.IBrew;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class DemonsBaneBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity instanceof EntityBlaze || entity instanceof EntityMagmaCube || entity instanceof EntityPigZombie || entity instanceof EntityGhast || entity instanceof EntityWither || entity instanceof EntityWitherSkeleton) {
			entity.attackEntityFrom(DamageSource.MAGIC, 13);
		}
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0xFFF5EE;
	}

	@Override
	public String getName() {
		return "demons_bane";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 14);
	}

}
