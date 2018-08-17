package com.bewitchment.client.jei.components;

import com.bewitchment.common.lib.LibMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class SpinnerCategory implements IRecipeCategory<SpinnerWrapper> {

	public static final String UID = LibMod.MOD_ID + ":spinner";
	private IDrawable bg;

	public SpinnerCategory(IGuiHelper igh) {
		bg = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_spinner.png"), 0, 0, 90, 36).setTextureSize(90, 36).build();
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.spinner");
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
	public void setRecipe(IRecipeLayout recipeLayout, SpinnerWrapper recipeWrapper, IIngredients ingredients) {
		for (int i = 0; i < recipeWrapper.input.length; i++) {
			recipeLayout.getItemStacks().init(i + 1, true, (i % 2) * 18, (i / 2) * 18);
			recipeLayout.getItemStacks().set(i + 1, Arrays.asList(recipeWrapper.input[i].getMatchingStacks()));
		}
		recipeLayout.getItemStacks().init(0, false, 72, 9);
		recipeLayout.getItemStacks().set(0, recipeWrapper.output);
	}
}
