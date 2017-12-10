package com.witchcraft.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

/**
 * This class was created by Joseph on 7/21/2017..
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */

public class OvenRecipe {

	private final ItemStack output1, output2;
	private final ItemStack needed;

	public OvenRecipe(ItemStack needed, ItemStack output1, ItemStack output2) {
		this.needed = needed;
		this.output1 = output1;
		this.output2 = output2;
	}

	public ItemStack getNeeded() {
		return needed.copy();
	}

	public Tuple<ItemStack, ItemStack> getOutputs() {
		return new Tuple<>(output1.copy(), output2.copy());
	}

	public boolean matches(ItemStack input) {
		return input != null && needed != null && needed.getItem() == input.getItem() && (needed.getItemDamage() == input.getItemDamage()
				|| input.getItemDamage() == WILDCARD_VALUE);
	}
}