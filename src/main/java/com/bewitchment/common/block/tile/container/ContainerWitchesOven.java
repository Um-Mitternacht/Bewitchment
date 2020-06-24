package com.bewitchment.common.block.tile.container;

import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.container.util.ModSlot;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesOven;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings("UnusedAssignment")
public class ContainerWitchesOven extends ModContainer {
	private final TileEntityWitchesOven tile;
	public int burnTime, fuelBurnTime, progress;

	public ContainerWitchesOven(InventoryPlayer inventory, TileEntityWitchesOven tile) {
		this.tile = tile;
		IItemHandler up = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		IItemHandler down = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		int ui = 0, di = 0;
		addSlotToContainer(new ModSlot(up, ui++, 44, 55)); //fuel
		addSlotToContainer(new ModSlot(up, ui++, 80, 55)); //jars
		addSlotToContainer(new ModSlot(up, ui++, 44, 19)); //input
		addSlotToContainer(new ModSlot(down, di++, 116, 19)); //output
		addSlotToContainer(new ModSlot(down, di++, 116, 55)); //fume output
		addPlayerSlots(inventory);
	}

	@Override
	protected void sendToListener(IContainerListener listener) {
		listener.sendWindowProperty(this, 0, tile.burnTime);
		listener.sendWindowProperty(this, 1, tile.fuelBurnTime);
		listener.sendWindowProperty(this, 2, tile.progress);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if (id == 0) burnTime = data;
		if (id == 1) fuelBurnTime = data;
		else progress = data;
	}
}