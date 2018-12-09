package com.bewitchment.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModContainer<T extends TileEntity> extends Container {
	private final T tileEntity;

	public ModContainer(T tileEntity) {
		this.tileEntity = tileEntity;
	}

	protected void addPlayerSlots(InventoryPlayer playerInventory, int x, int y) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(playerInventory, i, x + i * 18, y + 58));
		}
	}

	protected void addPlayerSlots(InventoryPlayer playerInventory) {
		this.addPlayerSlots(playerInventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !player.isSpectator();
	}

	public T getTileEntity() {
		return tileEntity;
	}

	/*
	 * An array with the number of ids to sync to the client
	 */
	protected int getFieldsToSync() {
		return 0;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		this.listeners.forEach(l -> sendChangesToListener(l));
	}

	private void sendChangesToListener(IContainerListener l) {
		for (int i = 0; i < getFieldsToSync(); i++) {
			l.sendWindowProperty(this, i, getFieldFromTile(i));
		}
	}

	// Called on servers, given an ID, this should return the corresponding value
	public int getFieldFromTile(int id) {
		return 0;
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		sendChangesToListener(listener);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		onFieldUpdated(id, data);
	}

	@SideOnly(Side.CLIENT)
	protected void onFieldUpdated(int id, int data) {

	}
}