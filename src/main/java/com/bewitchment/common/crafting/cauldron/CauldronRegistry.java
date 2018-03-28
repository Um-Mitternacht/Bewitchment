package com.bewitchment.common.crafting.cauldron;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.bewitchment.common.crafting.IngredientMultiOreDict;
import com.bewitchment.common.item.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CauldronRegistry {
	
	private static final HashMap<Ingredient, CauldronFoodValue> STEW_REGISTRY = new HashMap<>();
	
	// The less entries an Ingredient has, the higher priority it will be in the list
	private static final Comparator<Map.Entry<Ingredient, CauldronFoodValue>> STEW_INGREDIENT_PRIORITY = Map.Entry.<Ingredient, CauldronFoodValue>comparingByKey(Comparator.comparing(i -> i.getMatchingStacks().length)).reversed();
	
	public static void registerFoodValue(Ingredient ingredient, CauldronFoodValue value) {
		if (ingredient.getMatchingStacks().length > 0) {
			STEW_REGISTRY.put(ingredient, value);
		}
	}
	
	public static CauldronFoodValue getValue(ItemStack stack) {
		System.out.println(stack);
		return STEW_REGISTRY.entrySet().stream().filter(e -> e.getKey().apply(stack)).sorted(STEW_INGREDIENT_PRIORITY).map(e -> e.getValue()).peek(f -> System.out.println(f)).findFirst().orElse(null);
	}
	
	public static void init() { // TODO tune values
		registerFood(Ingredient.fromItem(Items.APPLE), 4, 2.4f);
		registerFood(Ingredient.fromItem(Items.BAKED_POTATO), 5, 6f);
		registerFood(Ingredient.fromItem(Items.BEETROOT), 1, 1.2f);
		registerFood(Ingredient.fromItem(Items.BREAD), 5, 6f);
		registerFood(Ingredient.fromItem(Items.CARROT), 3, 3.6f);
		registerFood(Ingredient.fromItem(Items.FISH), 2, 0.4f);
		registerFood(Ingredient.fromItem(Items.COOKED_CHICKEN), 6, 7.2f);
		registerFood(Ingredient.fromItem(Items.COOKED_FISH), 5, 6f);
		registerFood(Ingredient.fromItem(Items.COOKED_MUTTON), 6, 9.6f);
		registerFood(Ingredient.fromItem(Items.COOKED_PORKCHOP), 8, 12.8f);
		registerFood(Ingredient.fromItem(Items.COOKED_BEEF), 7, 12.8f);
		registerFood(Ingredient.fromItem(Items.COOKED_RABBIT), 5, 6f);
		registerFood(Ingredient.fromItem(Items.SPECKLED_MELON), 3, 7.6f);
		registerFood(Ingredient.fromItem(Items.GOLDEN_APPLE), 4, 9.6f);
		registerFood(Ingredient.fromItem(Items.GOLDEN_CARROT), 6, 14.4f);
		registerFood(Ingredient.fromItem(Items.MELON), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.POTATO), 1, 0.6f);
		registerFood(Ingredient.fromItem(Items.BEEF), 3, 1.8f);
		registerFood(Ingredient.fromItem(Items.CHICKEN), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.MUTTON), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.RABBIT), 3, 1.8f);
		registerFood(Ingredient.fromItem(Items.WHEAT_SEEDS), 2, 1f);
		registerFood(Ingredient.fromItem(Items.PUMPKIN_SEEDS), 2, 1.3f);
		registerFood(Ingredient.fromItem(Items.MELON_SEEDS), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.BEETROOT_SEEDS), 2, 1.1f);
		registerFood(Ingredient.fromItem(Items.NETHER_WART), 3, 1.6f);
		registerFood(Ingredient.fromItem(Items.EGG), 2, 1.2f);
		registerFood(Ingredient.fromItem(Items.SUGAR), 1, 0.5f);
		registerFood(Ingredient.fromItem(ModItems.garlic), 2, 0.8f);
		registerFood(Ingredient.fromItem(ModItems.silphium), 3, 1.3f);
		registerFood(Ingredient.fromItem(ModItems.kelp), 4, 3.3f);
		registerFood(Ingredient.fromItem(ModItems.salt), 1, 0.5f);
		registerFood(Ingredient.fromItem(ModItems.mint), 1, 0.7f);
		registerFood(Ingredient.fromItem(ModItems.tulsi), 1, 0.7f);
		registerFood(Ingredient.fromItem(ModItems.ginger), 3, 0.9f);
		registerFood(Ingredient.fromItem(ModItems.lavender), 1, 0.6f);
		registerFood(Ingredient.fromItem(ModItems.wormwood), 1, 0.8f);
		registerFood(Ingredient.fromItem(ModItems.white_sage), 2, 0.9f);
		registerFood(Ingredient.fromItem(ModItems.honey), 2, 1.3f);
		registerFood(new IngredientMultiOreDict("salt", "itemSalt", "dustSalt", "foodSalt", "listAllSalt"), 2, 1);
		// Why would you eat this?
		registerFood(Ingredient.fromItem(ModItems.heart), 6, 6.6f);
		registerFood(Ingredient.fromItem(ModItems.tongue_of_dog), 4, 4.4f);
		// Well, if you're starving...
		registerFood(Ingredient.fromItem(Items.ROTTEN_FLESH), 2, 1.4f);
	}
	
	private static void registerFood(Ingredient ingredient, int hunger, float saturation) {
		registerFoodValue(ingredient, new CauldronFoodValue(hunger, saturation));
	}
}
