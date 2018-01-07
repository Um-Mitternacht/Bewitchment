package com.bewitchment.client.gui.container;

import com.bewitchment.client.gui.container.slots.OutputSlot;
import com.bewitchment.common.tile.TileEntityBarrel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerBarrel extends ContainerBase {

	public final TileEntityBarrel te;
	public int data_a[] = {0, 0, 0, 0};

	public ContainerBarrel(InventoryPlayer pi, TileEntityBarrel barrel) {
		addPlayerSlots(pi);
		te = barrel;
		te.getRecipe();
		data_a[0] = barrel.getBrewingTime();
		data_a[1] = barrel.getPowerAbsorbed();
		data_a[2] = barrel.getTimeRequired();
		data_a[3] = barrel.getPowerRequired();
		addSlotToContainer(new OutputSlot(barrel.getInventory(), 0, 134, 43));
		for (int row = 0; row < 2; row++)
			for (int col = 0; col < 3; col++) {
				addSlotToContainer(new SlotBarrel(barrel.getInventory(), (row * 3 + col) + 1, 62 + (18 * col), 35 + (18 * row)));
			}
	}

	@Override
	public int getUpdatedFieldData(int id) {
		switch (id) {
			case 0:
				return te.getBrewingTime();
			case 1:
				return te.getPowerAbsorbed();
			case 2:
				return te.getTimeRequired();
			case 3:
				return te.getPowerRequired();
			default:
				return 0;
		}
	}

	@Override
	protected int[] getFieldsToSync() {
		return data_a;
	}

	@Override
	protected void updateField(int id, int data) {
		data_a[id] = data;
	}

	public class SlotBarrel extends Slot {
		public SlotBarrel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			te.checkRecipe();
		}
	}

}
