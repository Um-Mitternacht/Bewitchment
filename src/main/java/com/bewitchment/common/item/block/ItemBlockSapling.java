/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.item.block;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.tree.BlockModSapling.EnumSaplingType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSapling extends ItemBlock {

	public ItemBlockSapling() {
		super(ModBlocks.sapling);
		this.setRegistryName(ModBlocks.sapling.getRegistryName());
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.getMetadata() >= EnumSaplingType.values().length) {
			return super.getTranslationKey(stack);
		}
		return super.getTranslationKey(stack) + "_" + EnumSaplingType.values()[stack.getMetadata()].getName();
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

}
