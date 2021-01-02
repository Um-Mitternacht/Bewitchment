package com.bewitchment.common.block.plants;

import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBlueInkCap extends BlockBushSpreading {
	public BlockBlueInkCap() {
		super("blue_ink_cap");
		setSoundType(SoundType.SLIME);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (world.getWorldTime() >= 12500) {
			super.updateTick(world, pos, state, rand);
		}
	}
}
