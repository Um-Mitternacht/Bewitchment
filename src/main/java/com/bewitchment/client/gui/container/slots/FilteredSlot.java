package com.bewitchment.client.gui.container.slots;

import com.google.common.base.Predicate;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FilteredSlot extends Slot {

	Predicate<ItemStack> ing;

	public FilteredSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Predicate<ItemStack> ing) {
		super(inventoryIn, index, xPosition, yPosition);
		this.ing = ing;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return ing.apply(stack);
	}
}