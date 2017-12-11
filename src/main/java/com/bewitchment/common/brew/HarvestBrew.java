package com.bewitchment.common.brew;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class HarvestBrew extends BlockHitBrew {

	@Override
	public int getColor() {
		return 0xC48F31;
	}

	@Override
	public String getName() {
		return "harvest";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) ((float) amplifier / 2F);

		BlockPos posI = pos.add(box, 1, box);
		BlockPos posF = pos.add(-box, -1, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		int chance = 10 + amplifier * 2;
		int fortune = MathHelper.clamp(amplifier, 0, 5);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 1 || world.rand.nextBoolean();
			if (place && state.getBlock() instanceof BlockCrops) {
				BlockCrops crop = (BlockCrops) state.getBlock();
				if (crop.isMaxAge(state)) {
					crop.dropBlockAsItemWithChance(world, spot, state, chance, fortune);
					world.setBlockToAir(spot);
				}
			}
		}
	}
}
