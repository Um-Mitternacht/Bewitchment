package com.bewitchment.common.content.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CauldronCraftingRecipe {

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
		ArrayList<ItemStack> newList = new ArrayList<>(stacks);
		for (Ingredient ing : ingredients) {
			boolean found = false;
			for (int is = stacks.size() - 1; is >= 0; is--) {
				if (ing.apply(stacks.get(is))) {
					found = true;
					newList.remove(stacks.get(is));
					break;
				}
			}
			if (!found) {
				return false; // Not all items required were in the crafting
			}
		}
		if (newList.size() > 0) {
			return false;// One or more items were not part of the crafting
		}
		return fluid == fluidstack.getFluid();
	}

	public int getRequiredAmount() {
		return fluidAmount;
	}
	
	public abstract boolean hasItemOutput();
	
	public abstract boolean hasFluidOutput();
	
	public abstract ItemStack getItemResult();
	
	public abstract FluidStack getFluidResult();

}
