package com.bewitchment.common.crafting;

import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

public class IngredientMultiOreDict extends IngredientOr {

	public IngredientMultiOreDict(String... ingredients) {
		super(fromStrings(ingredients));
	}

	private static Ingredient[] fromStrings(String[] array) {
		Ingredient[] ingArr = new Ingredient[array.length];
		for (int i = 0; i < array.length; i++) {
			ingArr[i] = new OreIngredient(array[i]);
		}
		return ingArr;
	}

}
