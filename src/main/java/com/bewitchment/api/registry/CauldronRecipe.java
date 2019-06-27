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
		this.input = input;
		this.output = output;
	}
	
	public final boolean matches(ItemStackHandler input) {
		return Util.areISListsEqual(this.input, input);
	}
}