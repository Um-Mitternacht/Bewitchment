package com.witchcraft.common.brew;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class TillLandBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0x602312;
	}

	@Override
	public String getName() {
		return "till_land";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) ((float) amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && state.getBlock() == Blocks.GRASS && world.isAirBlock(spot.up())) {
				world.setBlockState(spot, Blocks.FARMLAND.getDefaultState(), 3);
			}
		}
	}
}
