package com.bewitchment.api.registry;

import com.bewitchment.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SpinningWheelRecipe extends IForgeRegistryEntry.Impl<SpinningWheelRecipe> {
	public final List<Ingredient> input;
	public final ItemStack output;

	public SpinningWheelRecipe(ResourceLocation name, List<Ingredient> input, ItemStack output) {
		setRegistryName(name);
		this.input = Util.expandList(input);
		this.output = output;
		if (this.input.size() > 4)
			throw new IllegalArgumentException("Input size for " + name.toString() + " is too big, must be 4 at most.");
	}

	public final boolean matches(ItemStackHandler input) {
		return Util.areISListsEqual(this.input, input);
	}

	public final boolean isValid(ItemStackHandler input, ItemStackHandler output) {
		return Util.canMerge(output.getStackInSlot(0), this.output);
	}

	public final void giveOutput(ItemStackHandler input, ItemStackHandler output) {
		for (int i = 0; i < input.getSlots(); i++) input.extractItem(i, 1, false);
		output.insertItem(0, this.output.copy(), false);
	}
}