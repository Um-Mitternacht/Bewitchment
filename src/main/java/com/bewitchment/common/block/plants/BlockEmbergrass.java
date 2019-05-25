package com.bewitchment.common.block.plants;

import com.bewitchment.Util;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEmbergrass extends BlockBush {
	public BlockEmbergrass() {
		super();
		setLightLevel(3 / 15f);
		Util.registerBlock(this, "embergrass", Material.PLANTS, SoundType.PLANT, 0, 0, "shears", 0);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextBoolean()) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}
}