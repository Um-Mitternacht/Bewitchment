package com.witchcraft.common.brew;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 12/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class RockPulverizeBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0x3a0017;
	}

	@Override
	public String getName() {
		return "rock_pulverize";
	}

	@SuppressWarnings("deprecation")
	@Override
	void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) ((float) amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextInt(3) == 0;
			if (place) {
				if (state.getBlock() == Blocks.GRAVEL) {
					world.setBlockState(spot, Blocks.SAND.getDefaultState(), 3);
				} else if (state.getBlock() == Blocks.STONE) {
					world.setBlockState(spot, Blocks.GRAVEL.getDefaultState(), 3);
				} else if (state.getBlock() == Blocks.STONEBRICK) {
					world.setBlockState(spot, Blocks.STONEBRICK.getStateFromMeta(2), 3);
				}
			}
		}
	}
}
