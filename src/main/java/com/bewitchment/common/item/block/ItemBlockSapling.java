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
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() >= EnumSaplingType.values().length)
			return super.getUnlocalizedName(stack);
		return super.getUnlocalizedName(stack) + "_" + EnumSaplingType.values()[stack.getMetadata()].getName();
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
