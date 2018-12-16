package com.bewitchment.client.jei.components;

import com.bewitchment.common.lib.LibMod;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

/**
 * Created by Joseph on 12/10/2018.
 */
public class DistilleryCategory implements IRecipeCategory<DistilleryWrapper> {

	public static final String UID = LibMod.MOD_ID + ":distillation";

	@Override
	public String getUid() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public String getModName() {
		return null;
	}

	@Override
	public IDrawable getBackground() {
		return null;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistilleryWrapper recipeWrapper, IIngredients ingredients) {

	}
}
