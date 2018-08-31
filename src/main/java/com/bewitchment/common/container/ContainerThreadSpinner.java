package com.bewitchment.common.container;

import com.bewitchment.common.container.slots.ModSlot;
import com.bewitchment.common.container.slots.SlotOutput;
import com.bewitchment.common.tile.tiles.TileEntityThreadSpinner;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerThreadSpinner extends ModContainer<TileEntityThreadSpinner> {

	public ContainerThreadSpinner(InventoryPlayer pi, TileEntityThreadSpinner tileEntity) {
		super(tileEntity);
		IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotOutput<>(tileEntity, handler, 0, 116, 34));
		addSlotToContainer(new ModSlot<>(tileEntity, handler, 1, 44, 25));
		addSlotToContainer(new ModSlot<>(tileEntity, handler, 2, 62, 25));
		addSlotToContainer(new ModSlot<>(tileEntity, handler, 3, 44, 43));
		addSlotToContainer(new ModSlot<>(tileEntity, handler, 4, 62, 43));
		addPlayerSlots(pi);
	}
}
