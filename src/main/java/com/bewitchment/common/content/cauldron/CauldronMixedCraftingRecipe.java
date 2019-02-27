package com.bewitchment.common.content.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CauldronMixedCraftingRecipe extends CauldronFluidCraftingRecipe {

	private ItemStack itemResult;

	public CauldronMixedCraftingRecipe(Fluid fluid, int fluidAmount, FluidStack fluidOutput, ItemStack itemOutput, Ingredient[] ingredient) {
		super(fluid, fluidAmount, fluidOutput, ingredient);
		this.itemResult = itemOutput.copy();
	}

	@Override
	public boolean hasItemOutput() {
		return true;
	}

	@Override
	public ItemStack getItemResult() {
		return itemResult.copy();
	}

}
