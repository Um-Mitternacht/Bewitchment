package com.bewitchment.common.block.tile.container;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.container.util.ModSlot;
import com.bewitchment.common.block.tile.entity.TileEntitySigilTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSigilTable extends ModContainer {
	private final TileEntitySigilTable tile;
	
	public ContainerSigilTable(InventoryPlayer inventory, TileEntitySigilTable tile) {
		this.tile = tile;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				addSlotToContainer(new ModSlot(tile.matrix, x + y * 5, 25 + x * 18, 9 + y * 18));
			}
		}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 168));
		addSlotToContainer(new ModSlot(tile.output, 0, 134, 45) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
			
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
				tile.decreaseStackAmount(1);
				return super.onTake(thePlayer, stack);
			}
		});
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		if (index == 61) {
			int maxStackSize = getMaxCraftableAmount();
			tile.decreaseStackAmount(maxStackSize);
			Util.giveItem(player, new ItemStack(tile.output.getStackInSlot(0).getItem(), maxStackSize));
			return ItemStack.EMPTY;
		}
		else return super.transferStackInSlot(player, index);
	}
	
	private int getMaxCraftableAmount() {
		int min = tile.matrix.getStackInSlot(0).getCount();
		for (int i = 0; i < tile.matrix.getSlots(); i++) {
			if (tile.matrix.getStackInSlot(i).getCount() < min) min = tile.matrix.getStackInSlot(i).getCount();
		}
		return min;
	}
}
