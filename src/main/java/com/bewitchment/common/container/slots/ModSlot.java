package com.bewitchment.common.container.slots;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ModSlot<T extends TileEntity> extends SlotItemHandler {
	private T tileEntity;

	public ModSlot(T tileEntity, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.tileEntity = tileEntity;
	}

	@Override
	public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_) {
		this.tileEntity.markDirty();
	}

	public T getTileEntity() {
		return tileEntity;
	}
}
