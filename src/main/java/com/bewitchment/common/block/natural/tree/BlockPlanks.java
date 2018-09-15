package com.bewitchment.common.block.natural.tree;

import com.bewitchment.common.block.BlockMod;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Original class created by Zabi94.
 */

public class BlockPlanks extends BlockMod {

	public BlockPlanks(String name) {
		super(name, Material.WOOD);
		this.setHardness(2.0f);
		this.setHarvestLevel("axe", 0);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}
}
