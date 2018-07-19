package com.bewitchment.common.container.slots;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class SlotOutput<T extends TileEntity> extends ModSlot<T> {
	public SlotOutput(T tileEntity, IItemHandler handler, int index, int xPosition, int yPosition) {
		super(tileEntity, handler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		return false;
	}
}
