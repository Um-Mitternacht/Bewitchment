package com.bewitchment.common.block.natural.tree;

import com.bewitchment.common.block.BlockMod;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Original class created by Zabi94.
 */

public class BlockPlanks extends BlockMod {

	public BlockPlanks(String name) {
		super(name, Material.WOOD);
		this.setHarvestLevel("axe", 0);
		this.setSoundType(SoundType.WOOD);
	}
}
