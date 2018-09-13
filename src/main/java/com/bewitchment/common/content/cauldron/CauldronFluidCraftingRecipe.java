package com.bewitchment.common.content.cauldron;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CauldronFluidCraftingRecipe extends CauldronCraftingRecipe {

	private FluidStack result;
	
	public CauldronFluidCraftingRecipe(Fluid fluid, int fluidAmount, FluidStack output, Ingredient[] ingredient) {
		super(fluid, fluidAmount, ingredient);
		this.result = output.copy();
	}

	@Override
	public boolean hasItemOutput() {
		return false;
	}

	@Override
	public boolean matches(List<ItemStack> stacks, FluidStack fluidstack) {
		return super.matches(stacks, fluidstack) && fluidstack.amount == this.getRequiredAmount();
	}
	
	@Override
	public boolean hasFluidOutput() {
		return true;
	}
	
	@Override
	public FluidStack getFluidResult() {
		return result.copy();
	}

	@Override
	public ItemStack getItemResult() {
		return null;
	}

}
