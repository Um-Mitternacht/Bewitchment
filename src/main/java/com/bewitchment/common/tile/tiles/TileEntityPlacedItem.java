package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPlacedItem extends ModTileEntity {

	private ItemStack stack = ItemStack.EMPTY;

	public void setItem(ItemStack itemstack) {
		stack = itemstack;
		markDirty();
		syncToClient();
	}

	public ItemStack getItem() {
		return stack.copy();
	}

	public ItemStack pop() {
		ItemStack stackOut = stack.copy();
		world.destroyBlock(pos, false);
		return stackOut;
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
		NBTTagCompound nbt = new NBTTagCompound();
		stack.writeToNBT(nbt);
		tag.setTag("item", nbt);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		stack = new ItemStack(tag.getCompoundTag("item"));
	}
}
