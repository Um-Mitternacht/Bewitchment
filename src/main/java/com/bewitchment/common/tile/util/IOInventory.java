package com.bewitchment.common.tile.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class IOInventory implements IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {
	
	ItemStackHandler inputs, outputs;
	
	public IOInventory(int inputsize, int outputsize) {
		inputs = new ItemStackHandler(inputsize);
		outputs = new ItemStackHandler(outputsize);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("inputs", inputs.serializeNBT());
		tag.setTag("outputs", outputs.serializeNBT());
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		inputs.deserializeNBT(nbt.getCompoundTag("inputs"));
		outputs.deserializeNBT(nbt.getCompoundTag("outputs"));
	}

	@Override
	public int getSlots() {
		return inputs.getSlots() + outputs.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot >= inputs.getSlots()) {
			return outputs.getStackInSlot(slot - inputs.getSlots());
		}
		return inputs.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot < inputs.getSlots()) {
			return inputs.insertItem(slot, stack, simulate);
		}
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slot >= inputs.getSlots()) {
			return outputs.extractItem(slot - inputs.getSlots(), amount, simulate);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (slot < inputs.getSlots()) {
			inputs.setStackInSlot(slot, stack);
		} else {
			outputs.setStackInSlot(slot - inputs.getSlots(), stack);
		}
	}
}
