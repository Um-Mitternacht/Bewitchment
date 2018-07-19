package com.bewitchment.common.container.slots;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class SlotFiltered<T extends TileEntity> extends ModSlot<T> {

	private Predicate<ItemStack> ing;

	public SlotFiltered(T tileEntity, IItemHandler handler, int index, int xPosition, int yPosition, Predicate<ItemStack> ing) {
		super(tileEntity, handler, index, xPosition, yPosition);
		this.ing = ing;
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		return ing.test(stack);
	}
}