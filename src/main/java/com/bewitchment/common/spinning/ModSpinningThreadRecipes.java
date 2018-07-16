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
	public static SpinningThreadRecipe witches_stitching;
	public static SpinningThreadRecipe diabolic_vein;
	public static SpinningThreadRecipe pure_filament;
	public static SpinningThreadRecipe soulstring;

	public static void init() {

		//Declare ingredients here
		Ingredient string = Ingredient.fromItem(Items.STRING);
		Ingredient straw = Ingredient.fromItem(Items.WHEAT);
		Ingredient gold_nugget = Ingredient.fromItem(Items.GOLD_NUGGET);
		Ingredient parrot_feather = Ingredient.fromItem(ModItems.chromatic_quill);
		Ingredient stitching = Ingredient.fromItem(ModItems.witches_stitching);
		Ingredient ectoplasm = Ingredient.fromItem(ModItems.ectoplasm);
		Ingredient soul_string = Ingredient.fromItem(ModItems.soul_string);

		//Declare recipes here
		web = new SpinningThreadRecipe(LibMod.MOD_ID, "spider_web", new ItemStack(Blocks.WEB), string, string, string);
		regal_silk = new SpinningThreadRecipe(LibMod.MOD_ID, "regal_silk", new ItemStack(ModItems.regal_silk, 12, 0), parrot_feather, Ingredient.fromStacks(new ItemStack(Blocks.WEB, 1, 0)), Ingredient.fromStacks(new ItemStack(Blocks.WEB, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 9)));
		gold_thread = new SpinningThreadRecipe(LibMod.MOD_ID, "gold_thread", new ItemStack(ModItems.golden_thread, 3, 0), straw, straw, Ingredient.fromStacks(new ItemStack(Blocks.HAY_BLOCK, 1, 0)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 9)));
		witches_stitching = new SpinningThreadRecipe(LibMod.MOD_ID, "witches_stitching", new ItemStack(ModItems.witches_stitching, 4), string, string, Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 2)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 2)));
		diabolic_vein = new SpinningThreadRecipe(LibMod.MOD_ID, "diabolic_vein", new ItemStack(ModItems.diabolic_vein, 4), soul_string, soul_string, Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 8)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 13)));
		pure_filament = new SpinningThreadRecipe(LibMod.MOD_ID, "pure_filament", new ItemStack(ModItems.pure_filament, 4), stitching, stitching, Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 7)), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, 7)));
		soulstring = new SpinningThreadRecipe(LibMod.MOD_ID, "soulstring", new ItemStack(ModItems.soul_string), stitching, stitching, Ingredient.fromStacks(new ItemStack(ModItems.fume, 2, 10)), ectoplasm);

		registerAll();
	}

	//Register recipes here
	//It may also be possible to hide recipes by not registering them... :thonk:
	public static void registerAll() {
		SpinningThreadRecipe.REGISTRY.registerAll(web);
		SpinningThreadRecipe.REGISTRY.registerAll(gold_thread);
		SpinningThreadRecipe.REGISTRY.registerAll(regal_silk);
		SpinningThreadRecipe.REGISTRY.registerAll(witches_stitching);
		SpinningThreadRecipe.REGISTRY.registerAll(diabolic_vein);
		SpinningThreadRecipe.REGISTRY.registerAll(pure_filament);
		SpinningThreadRecipe.REGISTRY.registerAll(soulstring);
	}
}
