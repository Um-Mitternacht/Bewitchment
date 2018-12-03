package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.ModSlot;
import com.bewitchment.common.container.slots.SlotOutput;
import com.bewitchment.common.tile.tiles.TileEntityDistillery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerDistillery extends ModContainer<TileEntityDistillery> {
	
	public int progress = 0;
	public int fluidLevel = 0;
	public int totalTime = 1;
	
	public ContainerDistillery(InventoryPlayer playerInventory, TileEntityDistillery tileEntity) {
		super(tileEntity);
		IItemHandler in = tileEntity.getInputInventory();
		IItemHandler out = tileEntity.getOutputInventory();
		IItemHandler container = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0; i < 3; i++) {
			this.addSlotToContainer(new ModSlot<>(tileEntity, in, i*2, 34, 18*(i+1)-1));
			this.addSlotToContainer(new ModSlot<>(tileEntity, in, 1 + i*2, 52, 18*(i+1)-1));
		}
		
		for (int i = 0; i < 3; i++) {
			this.addSlotToContainer(new SlotOutput<>(tileEntity, out, i*2, 124, 18*(i+1)-1));
			this.addSlotToContainer(new SlotOutput<>(tileEntity, out, 1 + i*2, 142, 18*(i+1)-1));
		}
		
		this.addSlotToContainer(new ModSlot<>(tileEntity, container, 0, 88, 53));
		this.addPlayerSlots(playerInventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
//		Slot slot = inventorySlots.get(index);
//		if (slot != null && slot.getHasStack()) {
//			ItemStack itemstack1 = slot.getStack();
//			itemstack = itemstack1.copy();
//			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size() - 2;
//			if (index < containerSlots) {
//				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
//				return ItemStack.EMPTY;
//			}
//			if (itemstack1.getCount() == 0) {
//				slot.putStack(ItemStack.EMPTY);
//			} else {
//				slot.onSlotChanged();
//			}
//			if (itemstack1.getCount() == itemstack.getCount()) {
//				return ItemStack.EMPTY;
//			}
//			slot.onTake(player, itemstack1);
//		}
		return itemstack;
	}
	
	@Override
	public int getFieldFromTile(int id) {
		if (id == 0) {
			return this.getTileEntity().getStartingTime()-this.getTileEntity().getProgress();
		} else if (id == 1) {
			return this.getTileEntity().getFluidLevel();
		} else if (id == 2) {
			return this.getTileEntity().getStartingTime();
		}
		return -1;
	}
	
	@Override
	protected int getFieldsToSync() {
		return 3;
	}
	
	@Override
	protected void onFieldUpdated(int id, int data) {
		switch (id) {
		case 0:
			progress = data;
			break;
		case 1:
			fluidLevel = data;
			break;
		case 2:
			if (data == 0) {
				data = -1;
			}
			totalTime = data;
			break;
		default: break;
		}
	}
}