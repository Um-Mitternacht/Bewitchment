package com.bewitchment.common.brew;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 12/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class PruneLeavesBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0x5bff6e;
	}

	@Override
	public String getName() {
		return "prune_leaves";
	}

	@Override
	void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextInt(3) == 0;
			if (place && state.getMaterial() == Material.LEAVES) {
				world.setBlockToAir(spot);
			}
		}
	}
}
