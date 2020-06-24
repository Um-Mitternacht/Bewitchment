package com.bewitchment.common.block.tile.container;

import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.container.util.ModSlot;
import com.bewitchment.common.block.tile.entity.TileEntityDistillery;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerDistillery extends ModContainer {
    private final TileEntityDistillery tile;
    public int burnTime, progress;

    public ContainerDistillery(InventoryPlayer inventory, TileEntityDistillery tile) {
        this.tile = tile;
        IItemHandler up = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler down = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        int ui = 0, di = 0;
        addSlotToContainer(new ModSlot(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH), 0, 80, 58));
        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new ModSlot(up, ui++, 18, 18 * (i + 1) - 1));
            addSlotToContainer(new ModSlot(up, ui++, 36, 18 * (i + 1) - 1));
            addSlotToContainer(new ModSlot(down, di++, 124, 18 * (i + 1) - 1));
            addSlotToContainer(new ModSlot(down, di++, 142, 18 * (i + 1) - 1));
        }
        addPlayerSlots(inventory);
    }

    @Override
    protected void sendToListener(IContainerListener listener) {
        listener.sendWindowProperty(this, 0, tile.burnTime);
        listener.sendWindowProperty(this, 1, tile.progress);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        if (id == 0) burnTime = data;
        else progress = data;
    }
}