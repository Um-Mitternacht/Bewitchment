package com.bewitchment.common.crafting;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ModOvenSmeltingRecipes {

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		final ItemStack ash = new ItemStack(ModItems.wood_ash);
		final ItemStack cloudy_oil = new ItemStack(ModItems.fume, 1, ItemFumes.Type.cloudy_oil.ordinal());

		//TODO: modify the fumeChances
		OvenSmeltingRecipe.REGISTRY.registerAll(
				new OvenSmeltingRecipe(rl("sapling_0"), Ingredient.fromStacks(new ItemStack(Blocks.SAPLING)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.oak_spirit.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_1"), Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 1)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.spruce_heart.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_2"), Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 2)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.birch_soul.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_3"), Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 3)), ash, cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_4"), Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 4)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.acacia_essence.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_5"), Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 5)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.oak_spirit.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("rotten_flesh"), Ingredient.fromStacks(new ItemStack(Items.ROTTEN_FLESH)), new ItemStack(Items.LEATHER), new ItemStack(ModItems.ectoplasm, 3), 0.85f), //
				new OvenSmeltingRecipe(rl("iron_ore"), Ingredient.fromStacks(new ItemStack(Blocks.IRON_ORE)), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_NUGGET, 4), 0.85f), //
				new OvenSmeltingRecipe(rl("gold_ore"), Ingredient.fromStacks(new ItemStack(Blocks.GOLD_ORE)), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET, 2), 0.85f), //
				new OvenSmeltingRecipe(rl("silver_ore"), Ingredient.fromStacks(new ItemStack(ModBlocks.silver_ore)), new ItemStack(ModItems.silver_ingot), new ItemStack(ModItems.silver_nugget, 3), 0.85f), //
				new OvenSmeltingRecipe(rl("log_0"), Ingredient.fromStacks(new ItemStack(Blocks.LOG)), new ItemStack(Items.COAL, 1, 1), ash, 0.85f), //
				new OvenSmeltingRecipe(rl("log_1"), Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 1)), new ItemStack(Items.COAL, 1, 1), ash, 0.85f), //
				new OvenSmeltingRecipe(rl("log_2"), Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 2)), new ItemStack(Items.COAL, 1, 1), ash, 0.85f), //
				new OvenSmeltingRecipe(rl("log_3"), Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 3)), new ItemStack(Items.COAL, 1, 1), ash, 0.85f), //
				new OvenSmeltingRecipe(rl("log2_0"), Ingredient.fromStacks(new ItemStack(Blocks.LOG2)), new ItemStack(Items.COAL, 1, 1), ash, 0.85f), //
				new OvenSmeltingRecipe(rl("log2_1"), Ingredient.fromStacks(new ItemStack(Blocks.LOG2, 1, 1)), new ItemStack(Items.COAL, 1, 1), new ItemStack(ModItems.wood_ash, 3), 0.85f), //
				new OvenSmeltingRecipe(rl("bone"), Ingredient.fromStacks(new ItemStack(Items.BONE)), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModItems.ectoplasm, 1), 0.85f), //
				new OvenSmeltingRecipe(rl("wheat"), Ingredient.fromStacks(new ItemStack(Items.WHEAT)), new ItemStack(Items.BREAD), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("rabbit"), Ingredient.fromStacks(new ItemStack(Items.RABBIT)), new ItemStack(Items.COOKED_RABBIT), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("porkchop"), Ingredient.fromStacks(new ItemStack(Items.PORKCHOP)), new ItemStack(Items.COOKED_PORKCHOP), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("beef"), Ingredient.fromStacks(new ItemStack(Items.BEEF)), new ItemStack(Items.COOKED_BEEF), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("chicken"), Ingredient.fromStacks(new ItemStack(Items.CHICKEN)), new ItemStack(Items.COOKED_CHICKEN), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("fish_0"), Ingredient.fromStacks(new ItemStack(Items.FISH)), new ItemStack(Items.COOKED_FISH), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("fish_1"), Ingredient.fromStacks(new ItemStack(Items.FISH, 1, 1)), new ItemStack(Items.COOKED_FISH, 1, 1), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("melon"), Ingredient.fromStacks(new ItemStack(Items.MELON)), new ItemStack(ModItems.grilled_watermelon), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("cactus"), Ingredient.fromStacks(new ItemStack(Blocks.CACTUS)), new ItemStack(Items.DYE, 1, 2), cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("chorus_fruit"), Ingredient.fromStacks(new ItemStack(Items.CHORUS_FRUIT)), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModItems.dimensional_sand, 2), 0.85f), //
				new OvenSmeltingRecipe(rl("mandrake_root"), Ingredient.fromStacks(new ItemStack(ModItems.mandrake_root)), ash, cloudy_oil, 0.85f), //
				new OvenSmeltingRecipe(rl("unfired_jar"), Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.unfired_jar.ordinal())), new ItemStack(ModItems.fume, 1, ItemFumes.Type.empty_jar.ordinal()), ash, 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_6"), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 3)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.reek_of_death.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_7"), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 1)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.bottled_magic.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_8"), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 2)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.vital_essence.ordinal()), 0.85f), //
				new OvenSmeltingRecipe(rl("sapling_9"), Ingredient.fromStacks(new ItemStack(ModBlocks.sapling, 1, 0)), ash, new ItemStack(ModItems.fume, 1, ItemFumes.Type.droplet_of_wisdom.ordinal()), 0.85f)//
		);
	}

	private static ResourceLocation rl(String name) {
		return new ResourceLocation(LibMod.MOD_ID, name);
	}
}
