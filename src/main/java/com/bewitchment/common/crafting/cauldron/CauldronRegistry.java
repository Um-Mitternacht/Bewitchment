package com.bewitchment.common.crafting.cauldron;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CauldronRegistry {
	
	private static final HashMap<Ingredient, CauldronFoodValue> STEW_REGISTRY = new HashMap<>();
	
	// The less entries an Ingredient has, the higher priority it will be in the list
	private static final Comparator<Map.Entry<Ingredient, CauldronFoodValue>> STEW_INGREDIENT_PRIORITY = Map.Entry.<Ingredient, CauldronFoodValue>comparingByKey(Comparator.comparing(i -> i.getMatchingStacks().length));
	
	public static void registerFoodValue(Ingredient ingredient, CauldronFoodValue value) {
		STEW_REGISTRY.put(ingredient, value);
	}
	
	public static CauldronFoodValue getValue(ItemStack stack) {
		return STEW_REGISTRY.entrySet().stream().filter(e -> e.getKey().apply(stack)).sorted(STEW_INGREDIENT_PRIORITY).map(e -> e.getValue()).findFirst().orElse(null);
	}
}
