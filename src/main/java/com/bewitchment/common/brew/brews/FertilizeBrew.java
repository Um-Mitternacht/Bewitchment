package com.bewitchment.common.brew.brews;

import com.bewitchment.common.brew.BlockHitBrew;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class FertilizeBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0xede5e3;
	}

	@Override
	public String getName() {
		return "fertilize";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && state.getBlock() instanceof IGrowable) {
				IGrowable crop = (IGrowable) state.getBlock();
				if (crop.canGrow(world, spot, state, false))
					crop.grow(world, world.rand, spot, state);
			}
		}
	}
}
