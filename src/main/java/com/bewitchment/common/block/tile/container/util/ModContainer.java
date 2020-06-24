package com.bewitchment.common.block.tile.container.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@SuppressWarnings("NullableProblems")
public abstract class ModContainer extends Container {
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) sendToListener(listener);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack0 = slot.getStack();
            stack = stack0.copy();
            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots && !mergeItemStack(stack0, containerSlots, inventorySlots.size(), true) || !mergeItemStack(stack0, 0, containerSlots, false))
                stack = ItemStack.EMPTY;
            if (stack0.getCount() == 0) slot.putStack(ItemStack.EMPTY);
            if (stack0.getCount() == stack.getCount()) stack = ItemStack.EMPTY;
            slot.onTake(player, stack0);
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return !player.isSpectator();
    }

    protected void addPlayerSlots(InventoryPlayer inventory) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
    }

    protected void sendToListener(IContainerListener listener) {
    }
}