package com.bewitchment.common.crafting;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.ModMaterials;
import com.bewitchment.common.item.magic.ItemFumes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class VanillaCrafting {

	private VanillaCrafting() {
	}

	public static void blocks() {

		GameRegistry.addSmelting(ModBlocks.silver_ore, new ItemStack(ModItems.silver_ingot, 1), 0.35F);
		GameRegistry.addSmelting(Blocks.SAPLING, new ItemStack(ModItems.wood_ash, 4), 0.15F);
		GameRegistry.addSmelting(ModItems.silver_scales, new ItemStack(ModItems.silver_nugget, 1), 0.20F);
		GameRegistry.addSmelting(Items.MELON, new ItemStack(ModItems.grilled_watermelon, 1), 0.45F);
		GameRegistry.addSmelting((new ItemStack(ModItems.fume, 1, ItemFumes.Type.unfired_jar.ordinal())), new ItemStack(ModItems.fume, 1, ItemFumes.Type.empty_jar.ordinal()), 0.45F);

		ModMaterials.TOOL_RITUAL.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.ARMOR_SILVER.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.TOOL_SILVER.setRepairItem(new ItemStack(ModItems.silver_ingot));
	}

	@SuppressWarnings({"unused", "WeakerAccess"})
	private static class ShapelessRecipe {

		private final List<Object> ingredients = new ArrayList<>();
		private ItemStack output;

		public ShapelessRecipe outputs(Block out) {
			return outputs(new ItemStack(out));
		}

		public ShapelessRecipe outputs(ItemStack out) {
			this.output = out;
			return this;
		}

		public ShapelessRecipe outputs(Item out) {
			return outputs(new ItemStack(out));
		}

		public ShapelessRecipe add(Block block) {
			ingredients.add(block);
			return this;
		}

		public ShapelessRecipe add(Item item) {
			ingredients.add(item);
			return this;
		}

		public ShapelessRecipe add(ItemStack stack) {
			ingredients.add(stack);
			return this;
		}

		public ShapelessRecipe add(String string) {
			ingredients.add(string);
			return this;
		}

		public void build() {
			if (ingredients.isEmpty())
				throw new IllegalArgumentException("You have to specify ingredients for the recipe!");
			if (output == null) throw new IllegalArgumentException("Output not specified!");

			//Fixme: Everything.
			//final ShapelessOreRecipe recipe = new ShapelessOreRecipe(output, ingredients.toArray());
			//CraftingManager.getInstance().getRecipeList().add(recipe);
		}
	}
}
