package com.bewitchment.common.tile.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class IOInventory implements IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {

	ItemStackHandlerRef inputs, outputs;

	public IOInventory(int inputsize, int outputsize) {
		inputs = new ItemStackHandlerRef(inputsize) {
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				inventoryChanged();
			}
		};
		outputs = new ItemStackHandlerRef(outputsize) {
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				inventoryChanged();
			}
		};
	}

	protected void inventoryChanged() {

	}

	public int getInputSlotsCount() {
		return inputs.getSlots();
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

	public ItemStack insertInOutputs(ItemStack stack, boolean simulate) {
		ItemStack remaining = stack.copy();
		for (int i = 0; i < outputs.getSlots() && !remaining.isEmpty(); i++) {
			remaining = outputs.insertItem(i, remaining, simulate);
		}
		return remaining;
	}

	public ItemStack insertInInputs(ItemStack stack, boolean simulate) {
		ItemStack remaining = stack.copy();
		for (int i = 0; i < inputs.getSlots() && !remaining.isEmpty(); i++) {
			remaining = inputs.insertItem(i, remaining, simulate);
		}
		return remaining;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (slot < inputs.getSlots()) {
			inputs.setStackInSlot(slot, stack);
		} else {
			outputs.setStackInSlot(slot - inputs.getSlots(), stack);
		}
	}

	public List<ItemStack> getInputs() {
		return ImmutableList.copyOf(inputs.getStacks());
	}

	public List<ItemStack> getOutputs() {
		return ImmutableList.copyOf(outputs.getStacks());
	}

	protected static class ItemStackHandlerRef extends ItemStackHandler {

		public ItemStackHandlerRef(int inputsize) {
			super(inputsize);
		}

		public NonNullList<ItemStack> getStacks() {
			return stacks;
		}

	}
}
