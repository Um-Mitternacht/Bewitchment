/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRevealingCandle extends ItemBlock {
	
	private boolean lit;

	public ItemBlockRevealingCandle(Block block, boolean lit) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.lit = lit;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return lit;
	}

}
