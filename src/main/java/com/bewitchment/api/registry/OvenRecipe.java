package com.bewitchment.api.registry;

import com.bewitchment.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class OvenRecipe extends IForgeRegistryEntry.Impl<OvenRecipe> {
	public final ItemStack input, output, byproduct;
	public final float byproductChance;
	public final boolean requiresJar;
	
	public OvenRecipe(ResourceLocation name, ItemStack input, ItemStack output, ItemStack byproduct, float byproductChance, boolean requiresJar) {
		setRegistryName(name);
		this.input = input;
		this.output = output;
		this.byproduct = byproduct;
		this.byproductChance = byproductChance;
		this.requiresJar = requiresJar;
	}
	
	public OvenRecipe(ResourceLocation name, ItemStack input, ItemStack output, ItemStack byproduct, float byproductChance) {
		this(name, input, output, byproduct, byproductChance, true);
	}
	
	public final boolean matches(ItemStack input) {
		return OreDictionary.itemMatches(input, this.input, true);
	}
	
	public final boolean isValid(ItemStackHandler input, ItemStackHandler output) {
		return (byproduct.isEmpty() || !input.getStackInSlot(2).isEmpty()) && Util.canMerge(output.getStackInSlot(0), this.output) && Util.canMerge(output.getStackInSlot(1), this.byproduct);
	}
	
	public final void giveOutput(Random rand, ItemStackHandler input, ItemStackHandler output) {
		input.extractItem(2, 1, false);
		output.insertItem(0, this.output.copy(), false);
		if (!byproduct.isEmpty() && rand.nextFloat() < byproductChance && (!input.getStackInSlot(1).isEmpty() || !requiresJar)) {
			if (requiresJar) input.extractItem(1, 1, false);
			output.insertItem(1, byproduct.copy(), false);
		}
	}
}