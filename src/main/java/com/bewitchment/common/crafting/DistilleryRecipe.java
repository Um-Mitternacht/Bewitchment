package com.bewitchment.common.crafting;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class DistilleryRecipe extends IForgeRegistryEntry.Impl<DistilleryRecipe> {

	private NonNullList<ItemStack> outputs = null;
	private int time = -1;
	private NonNullList<Ingredient> inputs = null;

	private DistilleryRecipe() {

	}

	public boolean matches(List<ItemStack> list) {
		int nonEmpty = 0;
		for (ItemStack is : list) {
			if (is.getCount() > 0) {
				nonEmpty++;
			}
		}
		if (nonEmpty != this.inputs.size()) {
			return false;
		}
		boolean[] found = new boolean[this.inputs.size()];
		ArrayList<ItemStack> comp = new ArrayList<>(list);
		for (int i = 0; i < this.inputs.size(); i++) {
			Ingredient current = this.inputs.get(i);
			for (int j = 0; j < comp.size(); j++) {
				ItemStack is = comp.get(j);
				if (current.apply(is)) {
					found[i] = true;
					comp.set(j, ItemStack.EMPTY);
					break;
				}
			}
		}
		for (boolean b : found) {
			if (!b) {
				return false;
			}
		}
		return true;
	}

	public int getTime() {
		return this.time;
	}

	public NonNullList<ItemStack> getOutputs() {
		return this.outputs;
	}

	public List<Ingredient> getInputs() {
		return this.inputs;
	}

	public static class Factory {

		private DistilleryRecipe recipe = new DistilleryRecipe();

		private Factory(ResourceLocation registryName) {
			this.recipe.setRegistryName(registryName);
		}

		public static Factory start(ResourceLocation registryName) {
			return new Factory(registryName);
		}

		public static Factory start(String registryName) {
			return start(new ResourceLocation(LibMod.MOD_ID, registryName));
		}

		public Factory withBaseProcessingTime(int time) {
			if (time <= 0) {
				throw new IllegalArgumentException("Time must be a positive integer");
			}
			this.recipe.time = time;
			return this;
		}

		public Factory withOutput(ItemStack... stacks) {
			if (stacks.length > 6) {
				throw new IllegalArgumentException("Too many outputs, you can only use 6 at most");
			}
			this.recipe.outputs = NonNullList.from(ItemStack.EMPTY, stacks);
			return this;
		}

		public Factory withInput(Ingredient... stacks) {
			if (stacks.length > 6) {
				throw new IllegalArgumentException("Recipes cannot have more than 6 ingredients");
			}
			for (Ingredient i : stacks) {
				if (i.getMatchingStacks().length == 0) {
					throw new IllegalArgumentException("No valid stack found");
				}
			}
			this.recipe.inputs = NonNullList.from(Ingredient.EMPTY, stacks);
			return this;
		}

		public Factory setNoOutput() {
			this.recipe.outputs = NonNullList.create();
			return this;
		}

		public DistilleryRecipe build() {
			if (this.recipe.time <= 0) {
				throw new IllegalStateException("Time was not set properly");
			}
			if (this.recipe.inputs == null) {
				throw new IllegalStateException("Inputs were not set");
			}
			if (this.recipe.outputs == null) {
				throw new IllegalStateException("Outputs were not set");
			}
			return this.recipe;
		}

	}

}
