package com.bewitchment.common.crafting;

import com.bewitchment.api.crafting.OvenSmeltingRecipe;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModOvenSmeltingRecipes {

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		final ItemStack ash = new ItemStack(ModItems.wood_ash);
		final ItemStack cloudy_oil = new ItemStack(ModItems.fume, 1, ItemFumes.Type.cloudy_oil.ordinal());

		//TODO: modify the fumeChances
		OvenSmeltingRecipe.REGISTRY.registerAll(
				new OvenSmeltingRecipe("sapling_0", new ItemStack(Blocks.SAPLING), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.oak_spirit.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_1", new ItemStack(Blocks.SAPLING, 1, 1), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.spruce_heart.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_2", new ItemStack(Blocks.SAPLING, 1, 2), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.birch_soul.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_3", new ItemStack(Blocks.SAPLING, 1, 3), ash, cloudy_oil, 85),
				new OvenSmeltingRecipe("sapling_4", new ItemStack(Blocks.SAPLING, 1, 4), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.acacia_essence.ordinal()), 85),
				new OvenSmeltingRecipe("sapling_5", new ItemStack(Blocks.SAPLING, 1, 5), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.oak_spirit.ordinal()), 85),
				new OvenSmeltingRecipe("rotten_flesh", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), new ItemStack(ModItems.ectoplasm, 3), 85),
				new OvenSmeltingRecipe("iron_ore", new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_NUGGET, 4), 85),
				new OvenSmeltingRecipe("gold_ore", new ItemStack(Blocks.GOLD_ORE), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET, 2), 85),
				new OvenSmeltingRecipe("silver_ore", new ItemStack(ModBlocks.silver_ore), new ItemStack(ModItems.silver_ingot), new ItemStack(ModItems.silver_nugget, 3), 85),
				new OvenSmeltingRecipe("log_0", new ItemStack(Blocks.LOG), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log_1", new ItemStack(Blocks.LOG, 1, 1), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log_2", new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log_3", new ItemStack(Blocks.LOG, 1, 3), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log2_0", new ItemStack(Blocks.LOG2), new ItemStack(Items.COAL, 1, 1), ash, 85),
				new OvenSmeltingRecipe("log2_1", new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Items.COAL, 1, 1), new ItemStack(ModItems.wood_ash, 3), 85),
				new OvenSmeltingRecipe("bone", new ItemStack(Items.BONE), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModItems.ectoplasm, 1), 85),
				new OvenSmeltingRecipe("wheat", new ItemStack(Items.WHEAT), new ItemStack(Items.BREAD), cloudy_oil, 85),
				new OvenSmeltingRecipe("rabbit", new ItemStack(Items.RABBIT), new ItemStack(Items.COOKED_RABBIT), cloudy_oil, 85),
				new OvenSmeltingRecipe("porkchop", new ItemStack(Items.PORKCHOP), new ItemStack(Items.COOKED_PORKCHOP), cloudy_oil, 85),
				new OvenSmeltingRecipe("beef", new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF), cloudy_oil, 85),
				new OvenSmeltingRecipe("chicken", new ItemStack(Items.CHICKEN), new ItemStack(Items.COOKED_CHICKEN), cloudy_oil, 85),
				new OvenSmeltingRecipe("fish_0", new ItemStack(Items.FISH), new ItemStack(Items.COOKED_FISH), cloudy_oil, 85),
				new OvenSmeltingRecipe("fish_1", new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1), cloudy_oil, 85),
				new OvenSmeltingRecipe("melon", new ItemStack(Items.MELON), new ItemStack(ModItems.grilled_watermelon), cloudy_oil, 85),
				new OvenSmeltingRecipe("cactus", new ItemStack(Blocks.CACTUS), new ItemStack(Items.DYE, 1, 2), cloudy_oil, 85),
				new OvenSmeltingRecipe("chorus_fruit", new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModItems.dimensional_sand, 2), 85),
				new OvenSmeltingRecipe("mandrake_root", new ItemStack(ModItems.mandrake_root), ash, cloudy_oil, 85),
				new OvenSmeltingRecipe("unfired_jar", new ItemStack(ModItems.fume, 1, ItemFumes.Type.unfired_jar.ordinal()), new ItemStack(ModItems.fume, 1, ItemFumes.Type.empty_jar.ordinal()), ash, 85)
		);
	}
}
