/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.item.block;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.tools.BlockBarrel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBlockBarrel extends ItemBlock {

	public ItemBlockBarrel() {
		super(ModBlocks.barrel);
		this.setRegistryName(ModBlocks.barrel.getRegistryName());
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE)
			return super.getUnlocalizedName(stack);
		return super.getUnlocalizedName(stack) + "_" + BlockBarrel.WoodType.values()[stack.getMetadata()].getName();
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < BlockBarrel.WoodType.values().length; i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

}
