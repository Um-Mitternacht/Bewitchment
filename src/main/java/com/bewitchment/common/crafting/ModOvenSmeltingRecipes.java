package com.bewitchment.common.crafting;

import com.bewitchment.api.crafting.OvenSmeltingRecipe;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ModOvenSmeltingRecipes {

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		final ItemStack ash = new ItemStack(ModItems.wood_ash);
		final ItemStack cloudy_oil = new ItemStack(ModItems.fume, 1, ItemFumes.Type.cloudy_oil.ordinal());

		//TODO: modify the fumeChances
		OvenSmeltingRecipe.REGISTRY.registerAll(
				new OvenSmeltingRecipe("sapling_0", Ingredient.fromStacks(new ItemStack(Blocks.SAPLING)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.oak_spirit.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_1", Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 1)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.spruce_heart.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_2", Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 2)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.birch_soul.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_3", Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 3)), ash, cloudy_oil, 85),
				new OvenSmeltingRecipe("sapling_4", Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 4)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.acacia_essence.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_5", Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 5)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.oak_spirit.ordinal()), 85),
				new OvenSmeltingRecipe("rotten_flesh", Ingredient.fromStacks(new ItemStack(Items.ROTTEN_FLESH)), new ItemStack(Items.LEATHER), new ItemStack(ModItems.ectoplasm, 3), 85),
				new OvenSmeltingRecipe("iron_ore", Ingredient.fromStacks(new ItemStack(Blocks.IRON_ORE)), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_NUGGET, 4), 85),
				new OvenSmeltingRecipe("gold_ore", Ingredient.fromStacks(new ItemStack(Blocks.GOLD_ORE)), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET, 2), 85),
				new OvenSmeltingRecipe("silver_ore", Ingredient.fromStacks(new ItemStack(ModBlocks.silver_ore)), new ItemStack(ModItems.silver_ingot), new ItemStack(ModItems.silver_nugget, 3), 85),
				new OvenSmeltingRecipe("log_0", Ingredient.fromStacks(new ItemStack(Blocks.LOG)), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log_1", Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 1)), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log_2", Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 2)), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log_3", Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 3)), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log2_0", Ingredient.fromStacks(new ItemStack(Blocks.LOG2)), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log2_1", Ingredient.fromStacks(new ItemStack(Blocks.LOG2, 1, 1)), new ItemStack(Items.COAL, 1, 1), new ItemStack(ModItems.wood_ash, 3), 85),
				new OvenSmeltingRecipe("bone", Ingredient.fromStacks(new ItemStack(Items.BONE)), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModItems.ectoplasm, 1), 85),
				new OvenSmeltingRecipe("wheat", Ingredient.fromStacks(new ItemStack(Items.WHEAT)), new ItemStack(Items.BREAD), cloudy_oil, 85),
				new OvenSmeltingRecipe("rabbit", Ingredient.fromStacks(new ItemStack(Items.RABBIT)), new ItemStack(Items.COOKED_RABBIT), cloudy_oil, 85),
				new OvenSmeltingRecipe("porkchop", Ingredient.fromStacks(new ItemStack(Items.PORKCHOP)), new ItemStack(Items.COOKED_PORKCHOP), cloudy_oil, 85),
				new OvenSmeltingRecipe("beef", Ingredient.fromStacks(new ItemStack(Items.BEEF)), new ItemStack(Items.COOKED_BEEF), cloudy_oil, 85),
				new OvenSmeltingRecipe("chicken", Ingredient.fromStacks(new ItemStack(Items.CHICKEN)), new ItemStack(Items.COOKED_CHICKEN), cloudy_oil, 85),
				new OvenSmeltingRecipe("fish_0", Ingredient.fromStacks(new ItemStack(Items.FISH)), new ItemStack(Items.COOKED_FISH), cloudy_oil, 85),
				new OvenSmeltingRecipe("fish_1", Ingredient.fromStacks(new ItemStack(Items.FISH, 1, 1)), new ItemStack(Items.COOKED_FISH, 1, 1), cloudy_oil, 85),
				new OvenSmeltingRecipe("melon", Ingredient.fromStacks(new ItemStack(Items.MELON)), new ItemStack(ModItems.grilled_watermelon), cloudy_oil, 85),
				new OvenSmeltingRecipe("cactus", Ingredient.fromStacks(new ItemStack(Blocks.CACTUS)), new ItemStack(Items.DYE, 1, 2), cloudy_oil, 85),
				new OvenSmeltingRecipe("chorus_fruit", Ingredient.fromStacks(new ItemStack(Items.CHORUS_FRUIT)), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModItems.dimensional_sand, 2), 85),
				new OvenSmeltingRecipe("mandrake_root", Ingredient.fromStacks(new ItemStack(ModItems.mandrake_root)), ash, cloudy_oil, 85),
				new OvenSmeltingRecipe("unfired_jar", Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.unfired_jar.ordinal())), new ItemStack(ModItems.fume, 1, ItemFumes.Type.empty_jar.ordinal()), ash, 85),
				new OvenSmeltingRecipe("sapling_6", Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 3)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.reek_of_death.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_7", Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 1)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.bottled_magic.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_8", Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 2)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.vital_essence.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_9", Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 0)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.droplet_of_wisdom.ordinal()), 85)
		);
	}
}
