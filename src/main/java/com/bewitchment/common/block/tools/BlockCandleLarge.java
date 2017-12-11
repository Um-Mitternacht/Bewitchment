package com.bewitchment.common.block.tools;

import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class BlockCandleLarge extends BlockCandle {

	private static final AxisAlignedBB LARGE_BOX = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);

	public BlockCandleLarge() {
		super(LibBlockName.CANDLE_LARGE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return LARGE_BOX;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		{
			for (int i = 0; i < 16; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public int getType() {
		return 2;
	}
}
