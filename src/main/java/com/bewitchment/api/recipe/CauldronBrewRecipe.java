package com.bewitchment.api.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * This class was created by Arekkuusu on 04/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class CauldronBrewRecipe extends FlawlessRecipe {

	public CauldronBrewRecipe(ItemStack result, Object... inputs) {
		super(result, inputs);
	}

	public boolean canTake(ItemStack stack) {
		return stack.getItem() == Items.GLASS_BOTTLE;
	}
}
