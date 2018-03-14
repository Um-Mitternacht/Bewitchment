package com.bewitchment.common.brew.brews;

import com.bewitchment.common.brew.BlockHitBrew;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class GrowFlowersBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0xff63ff;
	}

	@Override
	public String getName() {
		return "grow_flowers";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		Random rand = world.rand;
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextInt(3) == 0;
			if (place && state.getBlock() == Blocks.GRASS && world.isAirBlock(spot.up())) {
				world.getBiome(pos).plantFlower(world, rand, spot.up());
			}
		}
	}
}
