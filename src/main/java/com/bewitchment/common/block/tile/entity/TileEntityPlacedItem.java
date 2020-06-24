package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityPlacedItem extends ModTileEntity {
	private final ItemStackHandler inventory = new ItemStackHandler(1);

	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}
}