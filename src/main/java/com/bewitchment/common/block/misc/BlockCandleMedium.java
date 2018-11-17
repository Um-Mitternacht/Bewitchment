package com.bewitchment.common.block.misc;

import com.bewitchment.common.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockCandleMedium extends BlockCandle {

	private static final AxisAlignedBB MEDIUM_BOX = new AxisAlignedBB(0.31, 0, 0.31, 0.69, 0.75, 0.69);

	public BlockCandleMedium(String id, boolean lit) {
		super(id, lit);
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return MEDIUM_BOX.offset(state.getOffset(source, pos));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.candle_medium);
	}

	@Override
	public int getType() {
		return 1;
	}
}
