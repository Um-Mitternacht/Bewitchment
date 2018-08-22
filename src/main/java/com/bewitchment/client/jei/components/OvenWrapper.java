package com.bewitchment.client.jei.components;

import com.bewitchment.common.crafting.OvenSmeltingRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OvenWrapper implements IRecipeWrapper {

	private Ingredient input;
	private ItemStack[] output;

	public OvenWrapper(OvenSmeltingRecipe recipe) {
		input = recipe.getInput();
		output = new ItemStack[2];
		output[0] = recipe.getOutput();
		output[1] = recipe.getFumes();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<List<ItemStack>> inputs = new ArrayList<>();
		inputs.add(Arrays.asList(input.getMatchingStacks()));
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ArrayList<List<ItemStack>> outputs = new ArrayList<>();
		outputs.add(Arrays.asList(output));
		ingredients.setOutputLists(VanillaTypes.ITEM, outputs);
	}

	public Ingredient getInput() {
		return input;
	}

	public ItemStack[] getOutput() {
		return output;
	}
}
