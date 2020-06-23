package com.bewitchment.api.registry;

import com.bewitchment.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class CauldronRecipe extends IForgeRegistryEntry.Impl<CauldronRecipe> {
	public final List<Ingredient> input;
	public final List<ItemStack> output;
	
	public CauldronRecipe(ResourceLocation name, List<Ingredient> input, List<ItemStack> output) {
		setRegistryName(name);
		if (input.size() > 10) throw new IllegalArgumentException("CauldronRecipes can only have at most 10 input items");
		if (output.size() > 3) throw new IllegalArgumentException("CauldronRecipes can only have at most 3 output items");
		this.input = input;
		this.output = output;
	}
	
	public final boolean matches(ItemStackHandler input) {
		return Util.areISListsEqual(this.input, input);
	}
}