package com.bewitchment.common.crafting.oven;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Created by Joseph on 11/6/2017.
 */

//Todo: Add RNG for fumes, and add an optional setting for glass jars.
public class OvenCrafting {

	private static final OvenCrafting OVEN_RECIPES = new OvenCrafting();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, ItemStack> fumeList = Maps.<ItemStack, ItemStack>newHashMap();

	public OvenCrafting() {
		final ItemStack ash = new ItemStack(ModItems.wood_ash);
		final ItemStack cloudy_oil = new ItemStack(ModItems.fume, 1, 6);

		//Todo: Fumes for the special saplings? Maybe add toast?
		addSmeltingRecipe(new ItemStack(Blocks.SAPLING), ash, new ItemStack(ModItems.fume, 1, 2));
		addSmeltingRecipe(new ItemStack(Blocks.SAPLING, 1, 1), ash, new ItemStack(ModItems.fume, 1, 5));
		addSmeltingRecipe(new ItemStack(Blocks.SAPLING, 1, 2), ash, new ItemStack(ModItems.fume, 1, 3));
		addSmeltingRecipe(new ItemStack(Blocks.SAPLING, 1, 3), ash, cloudy_oil);
		addSmeltingRecipe(new ItemStack(Blocks.SAPLING, 1, 4), ash, new ItemStack(ModItems.fume, 1, 4));
		addSmeltingRecipe(new ItemStack(Blocks.SAPLING, 1, 5), ash, new ItemStack(ModItems.fume, 1, 2));
		addSmeltingRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), new ItemStack(ModItems.ectoplasm, 3));
		addSmeltingRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_NUGGET, 4));
		addSmeltingRecipe(new ItemStack(Blocks.GOLD_ORE), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET, 2));
		addSmeltingRecipe(new ItemStack(ModBlocks.silver_ore), new ItemStack(ModItems.silver_ingot), new ItemStack(ModItems.silver_nugget, 3));
		addSmeltingRecipe(new ItemStack(Blocks.LOG), new ItemStack(Items.COAL, 1, 1), ash);
		addSmeltingRecipe(new ItemStack(Blocks.LOG, 1, 1), new ItemStack(Items.COAL, 1, 1), ash);
		addSmeltingRecipe(new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Items.COAL, 1, 1), ash);
		addSmeltingRecipe(new ItemStack(Blocks.LOG, 1, 3), new ItemStack(Items.COAL, 1, 1), ash);
		addSmeltingRecipe(new ItemStack(Blocks.LOG2), new ItemStack(Items.COAL, 1, 1), ash);
		addSmeltingRecipe(new ItemStack(Blocks.LOG2, 1, 1), new ItemStack(Items.COAL, 1, 1), new ItemStack(ModItems.wood_ash, 3));
		addSmeltingRecipe(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModItems.ectoplasm, 1));
		addSmeltingRecipe(new ItemStack(Items.WHEAT), new ItemStack(Items.BREAD), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.RABBIT), new ItemStack(Items.COOKED_RABBIT), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(Items.COOKED_PORKCHOP), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.CHICKEN), new ItemStack(Items.COOKED_CHICKEN), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.FISH), new ItemStack(Items.COOKED_FISH), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.MELON), new ItemStack(ModItems.grilled_watermelon), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Blocks.CACTUS), new ItemStack(Items.DYE, 1, 2), cloudy_oil);
		addSmeltingRecipe(new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModItems.dimensional_sand, 2));
		addSmeltingRecipe(new ItemStack(ModItems.mandrake_root), ash, cloudy_oil);
		addSmeltingRecipe(new ItemStack(ModItems.fume, 1, 0), new ItemStack(ModItems.fume, 1, 1), ash);
	}

	public static OvenCrafting instance() {
		return OVEN_RECIPES;
	}

	/**
	 * @param input      the input stack
	 * @param output     what outputs
	 * @param outputFume the output for the fume slot
	 */
	public void addSmeltingRecipe(ItemStack input, ItemStack output, ItemStack outputFume) {
		if (getSmeltResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting recipe with conflicting input, please report this to the mod author: {} = {}", input, output);
			return;
		}
		this.smeltingList.put(input, output);
		this.fumeList.put(input, outputFume);
	}

	public ItemStack getSmeltResult(ItemStack stack) {
		for (Map.Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet()) {
			if (this.compareItemStacks(stack, entry.getKey())) {
				return entry.getValue().copy();
			}
		}

		return ItemStack.EMPTY;
	}

	public ItemStack getFumesResult(ItemStack stack) {
		for (Map.Entry<ItemStack, ItemStack> entry : this.fumeList.entrySet()) {
			if (this.compareItemStacks(stack, entry.getKey())) {
				return entry.getValue().copy();
			}
		}

		return ItemStack.EMPTY;
	}


	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getSmeltingList() {
		return this.smeltingList;
	}
}
