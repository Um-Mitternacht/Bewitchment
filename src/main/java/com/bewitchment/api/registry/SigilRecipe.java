package com.bewitchment.api.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class SigilRecipe extends IForgeRegistryEntry.Impl<SigilRecipe> {
	public final List<Ingredient> input;
	public final ItemStack output;

	public SigilRecipe(ResourceLocation name, List<Ingredient> input, ItemStack output) {
		if (input.size() != 25)
			throw new IllegalArgumentException("Input size for " + name.toString() + " is incorrect, must be 25");
		setRegistryName(name);
		this.input = input;
		this.output = output;
	}

	public final boolean matches(ItemStackHandler input) {
		for (int i = 0; i < this.input.size(); i++) {
			if (!this.input.get(i).apply(input.getStackInSlot(i))) return false;
		}
		return true;
	}
}
