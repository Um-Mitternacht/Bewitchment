package com.bewitchment.common.tile.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

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
		if (isItemValidForSlot(slot, stack)) {
			return super.insertItem(slot, stack, simulate);
		} else {
			return stack;
		}
	}

	public abstract boolean isItemValidForSlot(int slot, ItemStack stack);
}
