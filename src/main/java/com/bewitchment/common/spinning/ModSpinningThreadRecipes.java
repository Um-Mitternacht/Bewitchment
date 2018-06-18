package com.bewitchment.common.spinning;

import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ModSpinningThreadRecipes {

	//Register recipes here
	public static SpinningThreadRecipe web;
	public static SpinningThreadRecipe gold_thread;

	public static void init() {

		//Declare ingredients here
		Ingredient string = Ingredient.fromItem(Items.STRING);
		Ingredient straw = Ingredient.fromItem(Items.WHEAT);
		Ingredient gold_nugget = Ingredient.fromItem(Items.GOLD_NUGGET);

		//Declare recipes here
		web = new SpinningThreadRecipe(LibMod.MOD_ID, "spider_web", new ItemStack(Blocks.WEB), string, string, string);
		gold_thread = new SpinningThreadRecipe(LibMod.MOD_ID, "gold_thread", new ItemStack(ModItems.golden_thread), straw, string, gold_nugget);
		// soulstring = new SpinningThreadRecipe(LibMod.MOD_ID, "soulstring", new ItemStack(ModItems.soulstring), string, string, Ingredient.fromStacks(new ItemStack(ModItems.misc, 1, 5)), Ingredient.fromStacks(new ItemStack(ModItems.flowers, 1, 1)));

		registerAll();
	}

	//Register recipes here
	public static void registerAll() {
		SpinningThreadRecipe.REGISTRY.registerAll(web);
		SpinningThreadRecipe.REGISTRY.registerAll(gold_thread);
	}
}
