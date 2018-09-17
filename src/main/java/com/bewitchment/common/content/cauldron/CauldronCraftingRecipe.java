package com.bewitchment.common.content.cauldron;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CauldronCraftingRecipe {

	private List<List<ItemStack>> jeiCache;
	private Ingredient[] ingredients;
	private Fluid fluid;
	private int fluidAmount;

	// The ingredients must be ordered from the more strict to the least strict
	// eg: if a crafting requires both a dyeBlack and an ink sack (not ore dict)
	// the ink sack index should be lower than the dye index
	public CauldronCraftingRecipe(Fluid fluid, int fluidAmount, Ingredient... ingredient) {
		this.ingredients = ingredient;
		this.fluid = fluid;
		this.fluidAmount = fluidAmount;
		checkInputHiding();
	}

	// I heard you like for loops and O(+inf) algorithms... (jk, it shouldn't be that bad, hopefully)
	private void checkInputHiding() {
		for (int i = 0; i < ingredients.length - 1; i++) {
			for (ItemStack is : ingredients[i].getMatchingStacks()) {
				for (int j = i + 1; j < ingredients.length; j++) {
					if (ingredients[j].apply(is)) {
						if (ingredients[i].getMatchingStacks().length > ingredients[j].getMatchingStacks().length) {
							throw new IllegalArgumentException("Ingredient " + ingredients[i] + " hides ingredient " + ingredients[j] + " if the Stack used is " + is);
						}
					}
				}
			}
		}
	}

	public boolean matches(List<ItemStack> stacks, FluidStack fluidstack) {
		if (fluid != fluidstack.getFluid() || stacks.size() != ingredients.length) {
			return false;
		}
		ArrayList<Ingredient> newIngredientList = Lists.newArrayList(ingredients);
		ArrayList<ItemStack> stackList = Lists.newArrayList(stacks);
		for (int i = ingredients.length - 1; i >= 0; i--) {
			boolean found = false;
			Ingredient ing = newIngredientList.get(i);
			for (int j = stackList.size() - 1; j >= 0; j--) {
				ItemStack is = stackList.get(j);
				if (ing.apply(is)) {
					newIngredientList.remove(i);
					stackList.remove(is);
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	public int getRequiredFluidAmount() {
		return fluidAmount;
	}

	public List<List<ItemStack>> getJEIInput() {
		if (jeiCache == null) {
			jeiCache = Lists.newArrayList();
			HashMap<Ingredient, Integer> sizes = new HashMap<>();
			for (Ingredient i : ingredients) {
				if (sizes.containsKey(i)) {
					sizes.put(i, sizes.get(i) + 1);
				} else {
					sizes.put(i, 1);
				}
			}
			for (Ingredient i : sizes.keySet()) {
				List<ItemStack> l = Lists.newArrayList();
				for (ItemStack is : i.getMatchingStacks()) {
					l.add(is.copy());
				}
				l.forEach(is -> is.setCount(sizes.get(i)));
				jeiCache.add(l);
			}
		}
		return jeiCache;
	}

	public List<List<FluidStack>> getJEIFluidInput() {
		List<List<FluidStack>> result = Lists.newArrayList();
		result.add(Lists.newArrayList(new FluidStack(fluid, fluidAmount)));
		return result;
	}

	public abstract boolean hasItemOutput();

	public abstract boolean hasFluidOutput();

	public abstract ItemStack getItemResult();

	public abstract FluidStack getFluidResult();

}
