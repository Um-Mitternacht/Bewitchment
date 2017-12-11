package com.bewitchment.common.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

/**
 * This class was created by Arekkuusu on 27/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ShapedRecipe {

	private final Map<Character, Object> characters = new HashMap<>();
	private final List<String> rows = new ArrayList<>();
	private ItemStack out;
	private boolean mirror;

	public RecipeMapping grid(String row1, String row2, String row3) {
		addRows(row1, row2, row3);
		return new RecipeMapping(this);
	}

	private void addRows(String... r) {
		Collections.addAll(rows, r);
	}

	public RecipeMapping grid(String row1, String row2) {
		addRows(row1, row2);
		return new RecipeMapping(this);
	}

	public RecipeMapping grid(String row1) {
		addRows(row1);
		return new RecipeMapping(this);
	}

	private ShapedRecipe map(char character, Object object) {
		characters.put(character, object);
		return this;
	}

	private ShapedRecipe setOutput(ItemStack stack) {
		this.out = stack;
		return this;
	}

	public ShapedRecipe setMirror(boolean mirrorIn) {
		this.mirror = mirrorIn;
		return this;
	}

	public void build() {
		final List<Object> recipes = new ArrayList<>();

		if (mirror) {
			recipes.add(true);
		}

		if (rows.isEmpty())
			throw new IllegalArgumentException("There must be at least one row in the recipe, please report this to the mod author!");
		Collections.addAll(recipes, rows.toArray());

		characters.forEach((character, o) -> {
			recipes.add(character);
			recipes.add(o);
		});

	}

	public static class RecipeMapping {

		private ShapedRecipe recipe;

		public RecipeMapping(ShapedRecipe recipe) {
			this.recipe = recipe;
		}

		public RecipeMapping map(char c, Item item) {
			recipe.map(c, item);
			return this;
		}

		public RecipeMapping map(char c, ItemStack stack) {
			recipe.map(c, stack);
			return this;
		}

		public RecipeMapping map(char c, Block block) {
			recipe.map(c, block);
			return this;
		}

		public RecipeMapping map(char c, String ore) {
			recipe.map(c, ore);
			return this;
		}

		public ShapedRecipe outputs(Block out) {
			return recipe.setOutput(new ItemStack(out));
		}

		public ShapedRecipe outputs(Item out) {
			return recipe.setOutput(new ItemStack(out));
		}

		public ShapedRecipe outputs(ItemStack out) {
			return recipe.setOutput(out);
		}
	}
}