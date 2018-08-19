package com.bewitchment.common.crafting.util;

import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.oredict.OreIngredient;

import java.util.ArrayList;

public class IngredientMultiOreDict extends CompoundIngredient {

	public IngredientMultiOreDict(String... ingredients) {
		super(fromStrings(ingredients));
	}

	private static ArrayList<Ingredient> fromStrings(String[] array) {
		ArrayList<Ingredient> a = new ArrayList<>(array.length);
		for (int i = 0; i < array.length; i++) {
			a.add(new OreIngredient(array[i]));
		}
		return a;
	}

}
