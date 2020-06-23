package com.bewitchment.common.block.tile.container;

import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.container.util.ModSlot;
import com.bewitchment.common.block.tile.entity.TileEntityJuniperChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerJuniperChest extends ModContainer {
	private final TileEntityJuniperChest tile;
	
	public ContainerJuniperChest(InventoryPlayer inventory, TileEntityJuniperChest tile) {
		this.tile = tile;
		tile.using++;
		IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int index = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new ModSlot(handler, index++, 8 + x * 18, 18 + y * 18));
			}
		}
		addPlayerSlots(inventory);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		tile.using--;
	}
}