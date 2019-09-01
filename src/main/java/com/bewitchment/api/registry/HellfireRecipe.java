package com.bewitchment.api.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HellfireRecipe extends IForgeRegistryEntry.Impl<HellfireRecipe> {
	public final Ingredient input;
	public final ItemStack output;

	public HellfireRecipe(ResourceLocation name, Ingredient input, ItemStack output) {
		setRegistryName(name);
		this.input = input;
		this.output = output;
	}

	public final boolean matches(ItemStack input) {
		for (ItemStack stack : this.input.getMatchingStacks()) if (OreDictionary.itemMatches(stack, input, true)) return true;
		return false;
	}
}