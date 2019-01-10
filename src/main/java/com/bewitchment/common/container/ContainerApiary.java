package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.SlotFiltered;
import com.bewitchment.common.tile.tiles.TileEntityApiary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("ConstantConditions")
public class ContainerApiary extends ModContainer<TileEntityApiary> {

	private final IItemHandler apiaryInventory;

	public ContainerApiary(InventoryPlayer playerInventory, TileEntityApiary tileEntity) {
		super(tileEntity);
		apiaryInventory = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0; i < TileEntityApiary.ROWS; i++) {
			for (int j = 0; j < TileEntityApiary.COLUMNS; j++) {
				this.addSlotToContainer(new SlotOneItem(tileEntity, apiaryInventory, j + i * 6, 35 + j * 18, 16 + i * 18));
			}
		}
		this.addPlayerSlots(playerInventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(slotIndex);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotIndex < this.apiaryInventory.getSlots()) {
				if (!this.mergeItemStack(itemstack1, this.apiaryInventory.getSlots(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.apiaryInventory.getSlots(), false)) {
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

	private class SlotOneItem extends SlotFiltered<TileEntityApiary> {
		public SlotOneItem(TileEntityApiary tileEntity, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(tileEntity, itemHandler, index, xPosition, yPosition, s -> itemHandler.isItemValid(index, s));
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 1;
		}
	}
}
