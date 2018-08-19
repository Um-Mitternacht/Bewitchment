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

public class OvenCategory implements IRecipeCategory<OvenWrapper> {
	public static final String UID = LibMod.MOD_ID + ":oven";

	private IDrawable bg;

	public OvenCategory(IGuiHelper igh) {
		bg = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_oven.png"), 0, 0, 82, 54).setTextureSize(82, 54).build();
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.oven");
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
	public void setRecipe(IRecipeLayout recipeLayout, OvenWrapper ovenWrapper, IIngredients iIngredients) {
		recipeLayout.getItemStacks().init(0, true, 0, 0);
		recipeLayout.getItemStacks().set(0, Arrays.asList(ovenWrapper.getInput().getMatchingStacks()));
		recipeLayout.getItemStacks().init(1, true, 60, 4);
		recipeLayout.getItemStacks().set(1, ovenWrapper.getOutput()[0]);
		recipeLayout.getItemStacks().init(2, true, 60, 36);
		recipeLayout.getItemStacks().set(2, ovenWrapper.getOutput()[1]);
	}
}
