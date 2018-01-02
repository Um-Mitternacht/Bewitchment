package com.bewitchment.common.spinning;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ModSpinningThreadRecipes {

	public static SpinningThreadRecipe web;

	public static void init() {
		Ingredient string = Ingredient.fromItem(Items.STRING);
		web = new SpinningThreadRecipe(LibMod.MOD_ID, "spider_web", new ItemStack(Blocks.WEB), string, string, string);
		// soulstring = new SpinningThreadRecipe(LibMod.MOD_ID, "soulstring", new ItemStack(ModItems.soulstring), string, string, Ingredient.fromStacks(new ItemStack(ModItems.misc, 1, 5)), Ingredient.fromStacks(new ItemStack(ModItems.flowers, 1, 1)));

		registerAll();
	}

	public static void registerAll() {
		SpinningThreadRecipe.REGISTRY.registerAll(web);
	}
}
