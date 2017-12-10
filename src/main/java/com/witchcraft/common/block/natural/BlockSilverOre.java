package com.witchcraft.common.block.natural;

import com.witchcraft.common.block.BlockMod;
import com.witchcraft.common.core.WitchcraftCreativeTabs;
import com.witchcraft.common.lib.LibBlockName;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class BlockSilverOre extends BlockMod {

	public BlockSilverOre() {
		super(LibBlockName.SILVER_ORE, Material.ROCK);
		setResistance(3F);
		setHardness(3F);
		setCreativeTab(WitchcraftCreativeTabs.BLOCKS_CREATIVE_TAB);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		if (this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this)) {
			return 1 + RANDOM.nextInt(5);
		}
		return 0;
	}
}
