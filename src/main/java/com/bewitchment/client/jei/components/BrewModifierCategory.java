package com.bewitchment.client.jei.components;

import com.bewitchment.common.lib.LibMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class BrewModifierCategory implements IRecipeCategory<BrewModifierWrapper> {

	public static final String UID = LibMod.MOD_ID + ":brew_modifiers";

	private IDrawable bg;

	public BrewModifierCategory(IGuiHelper igh) {
		bg = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_brewing_modifier.png"), 0, 0, 140, 80).setTextureSize(140, 80).build();
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.brew_modifiers");
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
	public void setRecipe(IRecipeLayout recipeLayout, BrewModifierWrapper recipeWrapper, IIngredients ingredients) {

		IGuiItemStackGroup sg = recipeLayout.getItemStacks();

		sg.init(0, true, 61, 1);
		sg.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

		sg.init(1, true, 61, 33);
		sg.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}


}
