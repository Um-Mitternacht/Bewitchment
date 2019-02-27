package com.bewitchment.client.jei.components;

import com.bewitchment.common.content.cauldron.CauldronCraftingRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CauldronCraftingWrapper implements IRecipeWrapper {

	CauldronCraftingRecipe recipe;

	public CauldronCraftingWrapper(CauldronCraftingRecipe in) {
		recipe = in;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, recipe.getJEIInput());
		ingredients.setInputLists(VanillaTypes.FLUID, recipe.getJEIFluidInput());
		if (recipe.hasItemOutput()) {
			ingredients.setOutput(VanillaTypes.ITEM, recipe.getItemResult());
		} else {
			ingredients.setOutput(VanillaTypes.ITEM, ItemStack.EMPTY);
		}
		if (recipe.hasFluidOutput()) {
			ingredients.setOutput(VanillaTypes.FLUID, recipe.getFluidResult());
		}

	}

	public CauldronCraftingRecipe getOriginal() {
		return recipe;
	}

}
