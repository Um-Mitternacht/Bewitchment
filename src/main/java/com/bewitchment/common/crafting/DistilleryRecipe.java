package com.bewitchment.common.crafting;

import com.bewitchment.common.core.helper.Log;
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
	private Ingredient container = null;
	private NonNullList<Ingredient> inputs = null;

	private DistilleryRecipe() {

	}

	public boolean matches(List<ItemStack> inputsIn, ItemStack containerIn) {
		if (doesContainerMatch(containerIn)) {
			if (doInputsMatch(inputsIn)) {
				return true;
			}
		}
		return false;
	}

	private boolean doInputsMatch(List<ItemStack> list) {
		int nonEmpty = 0;
		for (ItemStack is : list) {
			if (is.getCount() > 0) {
				nonEmpty++;
			}
		}
		if (nonEmpty != inputs.size()) {
			return false;
		}
		boolean[] found = new boolean[inputs.size()];
		ArrayList<ItemStack> comp = new ArrayList<>(list);
		for (int i = 0; i < inputs.size(); i++) {
			Ingredient current = inputs.get(i);
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

	private boolean doesContainerMatch(ItemStack containerIn) {
		return (container == null && !containerIn.isEmpty()) || (container != null && container.apply(containerIn));
	}

	public int getTime() {
		return time;
	}

	public NonNullList<ItemStack> getOutputs() {
		return outputs;
	}

	public static class Factory {

		private DistilleryRecipe recipe = new DistilleryRecipe();

		private Factory(ResourceLocation registryName) {
			recipe.setRegistryName(registryName);
		}

		public static Factory start(ResourceLocation registryName) {
			return new Factory(registryName);
		}

		public static Factory start(String registryName) {
			return start(new ResourceLocation(LibMod.MOD_ID, registryName));
		}

		public Factory withBaseProcessingTime(int time) {
			if (time <= 0) {
				throw new RuntimeException("Time must be a positive integer");
			}
			recipe.time = time;
			return this;
		}

		public Factory withOutput(ItemStack... stacks) {
			if (stacks.length > 6) {
				throw new RuntimeException("Too many outputs, you can only use 6 at most");
			}
			recipe.outputs = NonNullList.from(ItemStack.EMPTY, stacks);
			return this;
		}

		public Factory withInput(Ingredient... stacks) {
			if (stacks.length > 6) {
				throw new RuntimeException("Recipes cannot have more than 6 ingredients");
			}
			for (Ingredient i : stacks) {
				if (i.getMatchingStacks().length == 0) {
					throw new RuntimeException("No valid stack found");
				}
			}
			recipe.inputs = NonNullList.from(Ingredient.EMPTY, stacks);
			return this;
		}

		public Factory setNoOutput() {
			recipe.outputs = NonNullList.create();
			return this;
		}

		public Factory setNoContainer() {
			recipe.container = Ingredient.EMPTY;
			return this;
		}

		public Factory setAnyContainer() {
			recipe.container = null;
			return this;
		}

		public Factory withContainer(Ingredient containerIn) {
			Log.w("The container option for distillery recipes is broken, reverting to anyContainer");
			if (containerIn.getMatchingStacks().length == 0) {
				throw new RuntimeException("No valid stack found");
			}
			recipe.container = containerIn;
			setAnyContainer();
			return this;
		}

		public DistilleryRecipe build() {
			if (recipe.time <= 0) {
				throw new RuntimeException("Time was not set properly");
			}
			if (recipe.inputs == null) {
				throw new RuntimeException("Inputs were not set");
			}
			if (recipe.outputs == null) {
				throw new RuntimeException("Outputs were not set");
			}
			return recipe;
		}

	}

}
