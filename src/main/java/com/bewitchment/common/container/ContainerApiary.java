package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.SlotFiltered;
import com.bewitchment.common.tile.tiles.TileEntityApiary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
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

	public ContainerApiary(InventoryPlayer playerInventory, TileEntityApiary tileEntity) {
		super(tileEntity);
		IItemHandler ih = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0; i < TileEntityApiary.ROWS; i++) {
			for (int j = 0; j < TileEntityApiary.COLUMNS; j++) {
				this.addSlotToContainer(new SlotOneItem(tileEntity, ih, j + i * 6, 62 + j * 18, 16 + i * 18));
			}
		}
		this.addPlayerSlots(playerInventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return ItemStack.EMPTY;
//		final Slot slot = inventorySlots.get(slotIndex);
//		ItemStack copy = ItemStack.EMPTY;
//
//		if (slot != null && slot.getHasStack()) {
//			final ItemStack original = slot.getStack();
//			copy = original.copy();
//
//			if (slotIndex == 0) {
//				if (!mergeItemStack(original, 19, 55, true)) return ItemStack.EMPTY;
//				slot.onSlotChange(original, copy);
//			} else if (slotIndex > 19) {
//				if (original.getCount() == 1 && !mergeItemStack(original, 0, 1, false)) return ItemStack.EMPTY;
//				slot.onSlotChange(original, copy);
//			} else {
//				if (!mergeItemStack(original, 19, 55, true)) return ItemStack.EMPTY;
//				slot.onSlotChange(original, copy);
//			}
//
//			if (original.getCount() == 0) {
//				slot.putStack(ItemStack.EMPTY);
//			} else {
//				slot.onSlotChanged();
//			}
//
//			if (original.getCount() == copy.getCount()) return ItemStack.EMPTY;
//
//			slot.onTake(player, original);
//		}
//
//		return copy;
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
