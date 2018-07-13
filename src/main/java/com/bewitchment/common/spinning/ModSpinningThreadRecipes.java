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
	public static SpinningThreadRecipe regal_silk;

	public static void init() {

		//Declare ingredients here
		Ingredient string = Ingredient.fromItem(Items.STRING);
		Ingredient straw = Ingredient.fromItem(Items.WHEAT);
		Ingredient gold_nugget = Ingredient.fromItem(Items.GOLD_NUGGET);
		Ingredient parrot_feather = Ingredient.fromItem(ModItems.chromatic_quill);

		//Declare recipes here
		web = new SpinningThreadRecipe(LibMod.MOD_ID, "spider_web", new ItemStack(Blocks.WEB), string, string, string);
	    regal_silk = new SpinningThreadRecipe(LibMod.MOD_ID, "regal_silk", new ItemStack(ModItems.regal_silk, 12, 0), parrot_feather, Ingredient.fromStacks(new ItemStack(Blocks.WEB, 1, 0)), Ingredient.fromStacks(new ItemStack(Blocks.WEB, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 9)));
		gold_thread = new SpinningThreadRecipe(LibMod.MOD_ID, "gold_thread", new ItemStack(ModItems.golden_thread, 3, 0), straw, straw, Ingredient.fromStacks(new ItemStack(Blocks.HAY_BLOCK, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 9)));
		// soulstring = new SpinningThreadRecipe(LibMod.MOD_ID, "soulstring", new ItemStack(ModItems.soulstring), string, string, Ingredient.fromStacks(new ItemStack(ModItems.misc, 1, 5)), Ingredient.fromStacks(new ItemStack(ModItems.flowers, 1, 1)));

		registerAll();
	}

	//Register recipes here
	//It may also be possible to hide recipes by not registering them... :thonk:
	public static void registerAll() {
		SpinningThreadRecipe.REGISTRY.registerAll(web);
		SpinningThreadRecipe.REGISTRY.registerAll(gold_thread);
		SpinningThreadRecipe.REGISTRY.registerAll(regal_silk);
	}
}
