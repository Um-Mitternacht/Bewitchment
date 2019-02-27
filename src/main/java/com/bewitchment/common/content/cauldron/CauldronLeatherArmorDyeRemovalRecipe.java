package com.bewitchment.common.content.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;

public class CauldronLeatherArmorDyeRemovalRecipe extends CauldronItemCraftingRecipe {

	ItemStack result;

	public CauldronLeatherArmorDyeRemovalRecipe(Fluid fluid, int fluidAmount, ItemStack output, ItemStack ingredient) {
		super(fluid, fluidAmount, output, new Ingredient[]{Ingredient.fromStacks(ingredient)});
		result = output;
	}

	@Override
	public ItemStack getItemResult(NonNullList<ItemStack> ingredients) {
		NBTTagCompound ingredientNBT = ingredients.get(0).getTagCompound();
		if (ingredientNBT != null && ingredientNBT.hasKey("display")) {
			NBTTagCompound displayNBT = ingredientNBT.getCompoundTag("display");
			if (displayNBT.hasKey("color")) {
				displayNBT.removeTag("color");
			}
		}
		return ingredients.get(0);
	}

	@Override
	public ItemStack getItemResult() {
		return result.copy();
	}
}
