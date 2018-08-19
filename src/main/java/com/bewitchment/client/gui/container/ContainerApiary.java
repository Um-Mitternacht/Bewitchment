package com.bewitchment.client.gui.container;

import com.bewitchment.common.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ContainerApiary extends Container {

	private final IInventory apiary;

	public ContainerApiary(InventoryPlayer playerInventory, IInventory inventory) {
		this.apiary = inventory;
		this.addSlotToContainer(new SlotBee(inventory, 0, 26, 34));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				this.addSlotToContainer(new SlotItem(inventory, j + i * 6 + 1, 62 + j * 18, 16 + i * 18));
			}
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.apiary);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		final Slot slot = inventorySlots.get(slotIndex);
		ItemStack copy = ItemStack.EMPTY;

		if (slot != null && slot.getHasStack()) {
			final ItemStack original = slot.getStack();
			copy = original.copy();

			if (slotIndex == 0) {
				if (!mergeItemStack(original, 19, 55, true)) return ItemStack.EMPTY;
				slot.onSlotChange(original, copy);
			} else if (slotIndex > 19) {
				if (original.getCount() == 1 && !mergeItemStack(original, 0, 1, false)) return ItemStack.EMPTY;
				slot.onSlotChange(original, copy);
			} else {
				if (!mergeItemStack(original, 19, 55, true)) return ItemStack.EMPTY;
				slot.onSlotChange(original, copy);
			}

			if (original.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (original.getCount() == copy.getCount()) return ItemStack.EMPTY;

			slot.onTake(player, original);
		}

		return copy;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !playerIn.isSpectator();
	}

	private class SlotBee extends Slot {

		SlotBee(IInventory inventoryIn, int slotIndex, int x, int y) {
			super(inventoryIn, slotIndex, x, y);
		}

		@Override
		public boolean isItemValid(@Nullable ItemStack stack) {
			return stack != null && stack.getItem() == ModItems.bee;
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 1;
		}
	}

	private class SlotItem extends Slot {

		SlotItem(IInventory inventoryIn, int slotIndex, int x, int y) {
			super(inventoryIn, slotIndex, x, y);
		}

		@Override
		public boolean isItemValid(@Nullable ItemStack stack) {
			return stack != null && (stack.getItem() == ModItems.honeycomb
					|| stack.getItem() == ModItems.empty_honeycomb
					|| stack.getItem() == ModItems.bee);
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 1;
		}
	}
}
