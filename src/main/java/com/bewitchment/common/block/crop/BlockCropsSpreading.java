package com.bewitchment.common.block.crop;

import com.bewitchment.common.block.util.ModBlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("NullableProblems")
public class BlockCropsSpreading extends ModBlockCrops {
	public BlockCropsSpreading(String name) {
		super(name);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if (isMaxAge(state) && rand.nextInt(10) <= 3) {
			int i = rand.nextInt(4);
			BlockPos pos0 = i == 0 ? pos.north() : i == 1 ? pos.south() : i == 2 ? pos.east() : pos.west();
			if (world.getBlockState(pos0.down()).getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, this) && world.getBlockState(pos0.down()).getBlock() == Blocks.FARMLAND && world.isAirBlock(pos0)) world.setBlockState(pos0, getDefaultState());
		}
	}
}