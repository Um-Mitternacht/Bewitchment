package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.ModSlot;
import com.bewitchment.common.container.slots.SlotOutput;
import com.bewitchment.common.tile.tiles.TileEntityBarrel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerBarrel extends ModContainer<TileEntityBarrel> {

	public ContainerBarrel(InventoryPlayer pi, TileEntityBarrel tileEntity) {
		super(tileEntity);
		addPlayerSlots(pi);
		tileEntity.getRecipe();
		IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotOutput<>(tileEntity, handler, 0, 134, 43));
		for (int row = 0; row < 2; row++)
			for (int col = 0; col < 3; col++) {
				addSlotToContainer(new SlotBarrel(tileEntity, handler, (row * 3 + col) + 1, 62 + (18 * col), 35 + (18 * row)));
			}
	}

	public class SlotBarrel extends ModSlot<TileEntityBarrel> {
		public SlotBarrel(TileEntityBarrel tileEntity, IItemHandler handler, int index, int xPosition, int yPosition) {
			super(tileEntity, handler, index, xPosition, yPosition);
		}

		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			getTileEntity().checkRecipe();
		}
	}
}
