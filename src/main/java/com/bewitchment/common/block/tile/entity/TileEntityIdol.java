package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityIdol extends ModTileEntity {
	public ItemStack stack = ItemStack.EMPTY;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("stack", stack.serializeNBT());
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		stack.deserializeNBT(tag.getCompoundTag("stack"));
		super.readFromNBT(tag);
	}
}