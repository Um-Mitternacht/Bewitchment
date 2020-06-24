package com.bewitchment.common.block.plants;

import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEmbergrass extends BlockBushSpreading {
	public BlockEmbergrass() {
		super("embergrass");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextBoolean())
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (!world.isRemote && entity instanceof EntityLivingBase && this == ModObjects.embergrass) entity.setFire(10);
	}
}