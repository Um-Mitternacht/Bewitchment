package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.inventory.IInventory;
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
		if (input.size() != 25) throw new IllegalArgumentException("Input size for " + name.toString() + " is incorrect, must be 25");
		setRegistryName(name);
		this.input = input;
		this.output = output;
	}

	public final boolean matches(IInventory input) {
		for (int i = 0; i < this.input.size(); i++) {
			if (!this.input.get(i).apply(input.getStackInSlot(i))) return false;
		}
		return true;
	}

	public final boolean isValid(ItemStackHandler output) {
		return Util.canMerge(output.getStackInSlot(0), this.output);
	}

	public final void giveOutput(ItemStackHandler input, ItemStackHandler output) {
		for (int i = 0; i < input.getSlots(); i++) input.extractItem(i, 1, false);
		output.insertItem(ModTileEntity.getFirstValidSlot(output, this.output), this.output.copy(), false);
	}
}
