package com.bewitchment.common.crafting.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class IngredientAnd extends Ingredient {

	Ingredient[] others;

	public IngredientAnd(Ingredient... ingredients) {
		others = ingredients;
	}

	@Override
	public boolean apply(ItemStack ing) {
		for (Ingredient i : others) {
			if (!i.apply(ing)) {
				return false;
			}
		}
		return true;
	}
}
