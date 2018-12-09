package com.bewitchment.common.tile.util;

import com.bewitchment.common.core.helper.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.function.Supplier;

public class JointInventoryWrapper implements IItemHandlerModifiable {

	//The index in the list is the outside slot, the integer is the wrapped handler slot
	ArrayList<Tuple<Supplier<ItemStackHandler>, Integer>> bindings = new ArrayList<>();
	ArrayList<Mode> modes = new ArrayList<>();

	@Override
	public int getSlots() {
		return bindings.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot >= bindings.size() || bindings.get(slot) == null) {
			return ItemStack.EMPTY;
		}
		Tuple<Supplier<ItemStackHandler>, Integer> res = bindings.get(slot);
		return res.getFirst().get().getStackInSlot(res.getSecond());
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot >= bindings.size() || bindings.get(slot) == null || !modes.get(slot).canInsert()) {
			return stack;
		}
		Tuple<Supplier<ItemStackHandler>, Integer> res = bindings.get(slot);
		return res.getFirst().get().insertItem(res.getSecond(), stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slot >= bindings.size() || bindings.get(slot) == null || !modes.get(slot).canExtract()) {
			return ItemStack.EMPTY;
		}
		Tuple<Supplier<ItemStackHandler>, Integer> res = bindings.get(slot);
		return res.getFirst().get().extractItem(res.getSecond(), amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		if (slot >= bindings.size() || bindings.get(slot) == null) {
			return 0;
		}
		Tuple<Supplier<ItemStackHandler>, Integer> res = bindings.get(slot);
		return res.getFirst().get().getSlotLimit(res.getSecond());
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (slot >= bindings.size() || bindings.get(slot) == null) {
			Log.w("Slot " + slot + " wasn't bound properly: " + stack);
		}
		Tuple<Supplier<ItemStackHandler>, Integer> res = bindings.get(slot);
		res.getFirst().get().setStackInSlot(res.getSecond(), stack);
	}

	public JointInventoryWrapper bind(Supplier<ItemStackHandler> handler, int slot, Mode mode) {
		this.bindings.add(new Tuple<Supplier<ItemStackHandler>, Integer>(handler, slot));
		this.modes.add(mode);
		return this;
	}

	public static enum Mode {

		INSERT(true, false), EXTRACT(false, true), BOTH(true, true), NONE(false, false);

		private boolean r_in, r_out;

		Mode(boolean in, boolean out) {
			r_in = in;
			r_out = out;
		}

		public boolean canInsert() {
			return r_in;
		}

		public boolean canExtract() {
			return r_out;
		}
	}
}
