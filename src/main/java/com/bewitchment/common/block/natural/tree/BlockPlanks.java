package com.bewitchment.common.block.natural.tree;

import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Original class created by Zabi94.
 */

public class BlockPlanks extends Block {

	public BlockPlanks(String name) {
		super(Material.WOOD);
		this.setUnlocalizedName("planks_" + name);
		this.setRegistryName(LibMod.MOD_ID, "planks_" + name);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(BewitchmentCreativeTabs.BLOCKS_CREATIVE_TAB);
		this.setSoundType(SoundType.WOOD);
	}
}
