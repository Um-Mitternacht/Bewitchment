package com.bewitchment.client.jei.components;

import com.bewitchment.common.lib.LibMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class RitualCategory implements IRecipeCategory<RitualWrapper> {

	public static final String UID = LibMod.MOD_ID + ":rituals";
	private IDrawable bg;

	public RitualCategory(IGuiHelper igh) {
		bg = igh.createBlankDrawable(140, 120);
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.rituals");
	}

	@Override
	public String getModName() {
		return LibMod.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return bg;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RitualWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> stacksIn = recipeWrapper.input;
		for (int i = 0; i < stacksIn.size(); i++) {
			recipeLayout.getItemStacks().init(i, true, 18 * i + (140 - 18 * stacksIn.size()) / 2, 15);
			recipeLayout.getItemStacks().set(i, stacksIn.get(i));
		}
		List<ItemStack> stacksOut = recipeWrapper.output;
		for (int i = 0; i < stacksOut.size(); i++) {
			recipeLayout.getItemStacks().init(i + stacksIn.size(), false, 18 * i + (140 - 18 * stacksOut.size()) / 2, 70);
			recipeLayout.getItemStacks().set(i + stacksIn.size(), stacksOut.get(i));
		}
	}
}
