package com.bewitchment.common.block.tile.container.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class ModSlot extends SlotItemHandler {
	private final int stackLimit;

	public ModSlot(IItemHandler handler, int index, int xPosition, int yPosition, int stackLimit) {
		super(handler, index, xPosition, yPosition);
		this.stackLimit = stackLimit;
	}

	public ModSlot(IItemHandler handler, int index, int xPosition, int yPosition) {
		this(handler, index, xPosition, yPosition, 64);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return getItemHandler().isItemValid(getSlotIndex(), stack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stackLimit;
	}
}