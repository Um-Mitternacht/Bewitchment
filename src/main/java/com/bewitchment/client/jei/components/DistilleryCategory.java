package com.bewitchment.client.jei.components;

import com.bewitchment.common.lib.LibMod;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;

/**
 * Created by Joseph on 12/10/2018.
 */
public class DistilleryCategory implements IRecipeCategory<DistilleryWrapper> {

	public static final String UID = LibMod.MOD_ID + ":distillation";
	private IDrawable bg;

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.distillery");
	}

	@Override
	public String getModName() {
		return LibMod.MOD_ID;
	}

	@Override
	public IDrawable getBackground() {
		return bg;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistilleryWrapper recipeWrapper, IIngredients ingredients) {

	}
}
