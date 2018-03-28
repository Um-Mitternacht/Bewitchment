package com.bewitchment.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class IngredientOr extends Ingredient {
	
	Ingredient[] others;
	
	public IngredientOr(Ingredient... ingredients) {
		others = ingredients;
	}
	
	@Override
	public boolean apply(ItemStack ing) {
		for (Ingredient i : others) {
			if (i.apply(ing)) {
				return true;
			}
		}
		return false;
	}
}
