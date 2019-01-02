package com.bewitchment.client.jei.components;

import com.bewitchment.common.crafting.DistilleryRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joseph on 12/10/2018.
 */
public class DistilleryWrapper implements IRecipeWrapper {

	List<Ingredient> input;
	List<ItemStack> output;

	public DistilleryWrapper(DistilleryRecipe recipe) {
		this.input = recipe.getInputs();
		this.output = recipe.getOutputs();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<List<ItemStack>> list = new ArrayList<List<ItemStack>>();
		for (Ingredient i : this.input) {
			list.add(Arrays.asList(i.getMatchingStacks()));
		}
		ingredients.setInputLists(VanillaTypes.ITEM, list);
		ingredients.setOutputs(VanillaTypes.ITEM, this.output);
	}
}