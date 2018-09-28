package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.tile.ModTileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPlacedItem extends ModTileEntity {
	
	private ItemStack stack = ItemStack.EMPTY;
	
	public void setItem(ItemStack itemstack) {
		stack = itemstack;
	}
	
	public ItemStack getItem() {
		return stack.copy();
	}
	
	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		readModSyncDataNBT(tag);
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		writeModSyncDataNBT(tag);
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		stack.writeToNBT(tag);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		stack = new ItemStack(tag);
	}

}
