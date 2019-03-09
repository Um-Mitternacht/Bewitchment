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

/**
 * Created by Joseph on 12/10/2018.
 */
public class DistilleryCategory implements IRecipeCategory<DistilleryWrapper> {

	public static final String UID = LibMod.MOD_ID + ":distillery";
	private IDrawable bg;

	public DistilleryCategory(IGuiHelper igh) {
		this.bg = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_distillery.png"), 0, 0, 93, 54).setTextureSize(93, 54).build();
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.bewitchment.category.distillery");
	}

	@Override
	public String getModName() {
		return LibMod.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return this.bg;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistilleryWrapper recipeWrapper, IIngredients ingredients) {
		for (int i = 0; i < recipeWrapper.input.size(); i++) {
			recipeLayout.getItemStacks().init(i, true, (i % 2) * 18, (i / 2) * 18);
			recipeLayout.getItemStacks().set(i, Arrays.asList(recipeWrapper.input.get(i).getMatchingStacks()));
		}
		for (int i = 0; i < recipeWrapper.output.size(); i++) {
			recipeLayout.getItemStacks().init(recipeWrapper.input.size() + i, false, 57 + ((i % 2) * 18), (i / 2) * 18);
			recipeLayout.getItemStacks().set(recipeWrapper.input.size() + i, recipeWrapper.output.get(i));
		}

	}
}
