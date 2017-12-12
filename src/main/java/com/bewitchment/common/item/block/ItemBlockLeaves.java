/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.item.block;

import com.bewitchment.common.block.natural.tree.BlockModSapling.EnumSaplingType;

import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemStack;

public class ItemBlockLeaves extends ItemLeaves {

	public ItemBlockLeaves(BlockLeaves block) {
		super(block);
		this.setHasSubtypes(false);
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName("leaves");// TODO lookup lib for name
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile.leaves_" + EnumSaplingType.values()[stack.getMetadata()].getName();// TODO lookup lib for name
	}

}
