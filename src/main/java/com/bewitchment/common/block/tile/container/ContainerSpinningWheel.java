package com.bewitchment.common.block.tile.container;

import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.container.util.ModSlot;
import com.bewitchment.common.block.tile.entity.TileEntitySpinningWheel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings("UnusedAssignment")
public class ContainerSpinningWheel extends ModContainer {
	private final TileEntitySpinningWheel tile;
	public int progress;

	public ContainerSpinningWheel(InventoryPlayer inventory, TileEntitySpinningWheel tile) {
		this.tile = tile;
		IItemHandler up = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		IItemHandler down = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		int ui = 0, di = 0;
		addSlotToContainer(new ModSlot(up, ui++, 44, 25));
		addSlotToContainer(new ModSlot(up, ui++, 44, 43));
		addSlotToContainer(new ModSlot(up, ui++, 62, 25));
		addSlotToContainer(new ModSlot(up, ui++, 62, 43));
		addSlotToContainer(new ModSlot(down, di++, 116, 25));
		addSlotToContainer(new ModSlot(down, di++, 116, 43));
		addPlayerSlots(inventory);
	}

	@Override
	protected void sendToListener(IContainerListener listener) {
		listener.sendWindowProperty(this, 0, tile.progress);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		progress = data;
	}
}