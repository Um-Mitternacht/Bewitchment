package com.bewitchment.common.crafting.cauldron;

import java.util.*;

import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.crafting.CauldronCraftingRecipe;
import com.bewitchment.common.crafting.IngredientMultiOreDict;
import com.bewitchment.common.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CauldronRegistry {
	
	private static final HashMap<Ingredient, CauldronFoodValue> STEW_REGISTRY = new HashMap<>();
	private static final ArrayList<CauldronCraftingRecipe> CRAFTING_REGISTRY = new ArrayList<>();
	
	// The less entries an Ingredient has, the higher priority it will be in the list
	private static final Comparator<Map.Entry<Ingredient, CauldronFoodValue>> STEW_INGREDIENT_PRIORITY = Map.Entry.<Ingredient, CauldronFoodValue>comparingByKey(Comparator.comparing(i -> i.getMatchingStacks().length)).reversed();
	
	public static void registerFoodValue(Ingredient ingredient, CauldronFoodValue value) {
		if (ingredient.getMatchingStacks().length > 0) {
			STEW_REGISTRY.put(ingredient, value);
		}
	}
	
	public static void registerCauldronCrafting(Fluid fluid, ItemStack output, Ingredient... ingredients) {
		CRAFTING_REGISTRY.add(new CauldronCraftingRecipe(fluid, output, ingredients));
	}
	
	public static CauldronFoodValue getCauldronFoodValue(ItemStack stack) {
		return STEW_REGISTRY.entrySet().stream().filter(e -> e.getKey().apply(stack)).sorted(STEW_INGREDIENT_PRIORITY).map(e -> e.getValue()).findFirst().orElse(null);
	}
	
	public static Optional<CauldronCraftingRecipe> getCraftingResult(FluidStack fluid, List<ItemStack> stacks) {
		return CRAFTING_REGISTRY.stream().filter(r -> r.matches(stacks, fluid)).findFirst();
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
		registerFood(Ingredient.fromItem(ModItems.heart), 6, 6.6f);
		registerFood(Ingredient.fromItem(ModItems.tongue_of_dog), 4, 4.4f);
		registerFood(Ingredient.fromItem(Items.ROTTEN_FLESH), 2, 1.4f);
		
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.LEATHER_HELMET), noMeta(Items.LEATHER_HELMET));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.LEATHER_CHESTPLATE), noMeta(Items.LEATHER_CHESTPLATE));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.LEATHER_LEGGINGS), noMeta(Items.LEATHER_LEGGINGS));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.LEATHER_BOOTS), noMeta(Items.LEATHER_BOOTS));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.SHIELD), noMeta(Items.SHIELD));
		// Miscellaneous water-based recipes
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Blocks.STICKY_PISTON, 1, 0), Ingredient.fromStacks(new ItemStack(Blocks.PISTON, 1, 0)));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Blocks.SPONGE, 1, 1), Ingredient.fromStacks(new ItemStack(Blocks.SPONGE, 1, 0)));
		// Cooking with Oil
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.COOKED_PORKCHOP), Ingredient.fromItem(Items.PORKCHOP));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.COOKED_MUTTON), Ingredient.fromItem(Items.MUTTON));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.COOKED_RABBIT), Ingredient.fromItem(Items.RABBIT));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.COOKED_CHICKEN), Ingredient.fromItem(Items.CHICKEN));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.COOKED_BEEF), Ingredient.fromItem(Items.BEEF));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.COOKED_FISH), Ingredient.fromStacks(new ItemStack(Items.FISH, 1, 0)));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.BAKED_POTATO), Ingredient.fromItem(Items.POTATO));
		registerCauldronCrafting(Fluids.MUNDANE_OIL, new ItemStack(Items.FISH, 1, 1), Ingredient.fromStacks(new ItemStack(Items.COOKED_FISH, 1, 1)));
		// Cooking and Processing with Water
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(ModItems.wax), Ingredient.fromItem(ModItems.empty_honeycomb));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(ModItems.honey), Ingredient.fromItem(ModItems.honeycomb));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.SLIME_BALL), Ingredient.fromItem(ModItems.hoof));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(ModItems.catechu, 6, 0), Ingredient.fromStacks(new ItemStack(Blocks.LOG2, 1, 0)));
		registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(ModItems.absinthe_green), Ingredient.fromStacks(new ItemStack(ModItems.wormwood, 1, 0)));
		// Banner pattern removal
		for (int i = 0; i < 16; i++) {
			registerCauldronCrafting(FluidRegistry.WATER, new ItemStack(Items.BANNER, 1, i), Ingredient.fromStacks(new ItemStack(Items.BANNER, 1, i)));
		}
		
	}
	
	private static Ingredient noMeta(Item i) {
		return Ingredient.fromStacks(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	private static void registerFood(Ingredient ingredient, int hunger, float saturation) {
		registerFoodValue(ingredient, new CauldronFoodValue(hunger, saturation));
	}
}
