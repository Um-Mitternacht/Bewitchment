package com.bewitchment.common.tile.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

@Deprecated
public abstract class AutomatableInventory extends ItemStackHandler {

	public AutomatableInventory() {
	}

	public AutomatableInventory(int size) {
		super(size);
	}

	public AutomatableInventory(NonNullList<ItemStack> stacks) {
		super(stacks);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (canInsertItemInSlot(slot, stack)) {
			return super.insertItem(slot, stack, simulate);
		}
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (canExtractFromSlot(slot)) {
			return super.extractItem(slot, amount, simulate);
		}
		return ItemStack.EMPTY;
	}

	public abstract boolean canInsertItemInSlot(int slot, ItemStack stack);

	public boolean canExtractFromSlot(int slot) {
		return true;
	}
}
