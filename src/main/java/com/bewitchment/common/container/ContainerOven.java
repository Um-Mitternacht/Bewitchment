package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.ModSlot;
import com.bewitchment.common.container.slots.SlotFiltered;
import com.bewitchment.common.container.slots.SlotOutput;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.tile.tiles.TileEntityOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by Joseph on 7/17/2017.
 */
public class ContainerOven extends ModContainer<TileEntityOven> {

	public int[] gui_data = new int[4];

	public ContainerOven(InventoryPlayer playerInventory, TileEntityOven tileEntity) {
		super(tileEntity);
		IItemHandler handlerUp = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		IItemHandler handlerDown = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

		//input slot
		this.addSlotToContainer(new ModSlot<>(tileEntity, handlerUp, 0, 44, 19));
		//fuel slot
		this.addSlotToContainer(new SlotFiltered<>(tileEntity, handlerUp, 1, 44, 55, TileEntityFurnace::isItemFuel));
		//jar slot
		this.addSlotToContainer(new SlotFiltered<>(tileEntity, handlerUp, 2, 80, 55, stack -> stack.getItem() == ModItems.fume && stack.getMetadata() == ItemFumes.Type.empty_jar.ordinal()));
		//output slot
		this.addSlotToContainer(new SlotOutput<>(tileEntity, handlerDown, 0, 116, 19));
		//fume slot
		this.addSlotToContainer(new SlotOutput<>(tileEntity, handlerDown, 1, 116, 55));

		this.addPlayerSlots(playerInventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size() - 2;
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}

	@Override
	public int getFieldFromTile(int id) {
		if (id == 0) {
			return this.getTileEntity().getBurnTime();
		} else if (id == 1) {
			return this.getTileEntity().getItemBurnTime();
		} else if (id == 2) {
			return this.getTileEntity().getWork();
		} else if (id == 3) {
			return this.getTileEntity().isBurning() ? 1 : 0;
		}
		return -1;
	}

	@Override
	protected int getFieldsToSync() {
		return 4;
	}

	@Override
	protected void onFieldUpdated(int id, int data) {
		if (id >= 0 && id < 4) {
			gui_data[id] = data;
		}
	}
}