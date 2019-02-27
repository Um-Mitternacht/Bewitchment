package com.bewitchment.common.content.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CauldronItemCraftingRecipe extends CauldronCraftingRecipe {

	private ItemStack result;

	public CauldronItemCraftingRecipe(Fluid fluid, int fluidAmount, ItemStack output, Ingredient[] ingredient) {
		super(fluid, fluidAmount, ingredient);
		result = output.copy();
	}

	@Override
	public boolean hasItemOutput() {
		return true;
	}

	@Override
	public boolean hasFluidOutput() {
		return false;
	}

	@Override
	public ItemStack getItemResult() {
		return result.copy();
	}

	@Override
	public FluidStack getFluidResult() {
		return null;
	}

}
