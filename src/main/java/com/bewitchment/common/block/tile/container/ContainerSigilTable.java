package com.bewitchment.common.block.tile.container;

import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.entity.TileEntitySigilTable;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerSigilTable extends ModContainer {
	private final TileEntitySigilTable tile;

	public ContainerSigilTable(InventoryPlayer inventory, TileEntitySigilTable tile) {
		this.tile = tile;
		addPlayerSlots(inventory);
	}
}
