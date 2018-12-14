package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.SlotFiltered;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.tiles.TileEntityApiary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Predicate;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("ConstantConditions")
public class ContainerApiary extends ModContainer<TileEntityApiary> {

	public ContainerApiary(InventoryPlayer playerInventory, TileEntityApiary tileEntity) {
		super(tileEntity);
		final IItemHandler handlerUp = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		final IItemHandler handlerDown = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

		this.addSlotToContainer(new SlotOneItem<>(tileEntity, handlerUp, 0, 26, 34, stack -> stack != null));

		Predicate<ItemStack> predicate = input -> input != null && (input.getItem() == ModItems.honeycomb || input.getItem() == ModItems.empty_honeycomb);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				this.addSlotToContainer(new SlotOneItem<>(tileEntity, handlerDown, j + i * 6, 62 + j * 18, 16 + i * 18, predicate));
			}
		}

		this.addPlayerSlots(playerInventory);
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

	private class SlotOneItem<T extends TileEntity> extends SlotFiltered<T> {
		public SlotOneItem(T tileEntity, IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> predicate) {
			super(tileEntity, itemHandler, index, xPosition, yPosition, predicate);
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 1;
		}
	}
}