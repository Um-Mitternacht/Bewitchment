package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Brew;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

@SuppressWarnings("NullableProblems")
public class BrewCategory implements IRecipeCategory<BrewCategory.Wrapper> {
	public static final String UID = "jei.brew";

	private final IDrawable bg;

	public BrewCategory(IGuiHelper helper) {
		bg = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_brew.png"), 0, 0, 128, 32).setTextureSize(128, 32).build();
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format(UID);
	}

	@Override
	public String getModName() {
		return Bewitchment.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return bg;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, false, 55, 2);
		recipeLayout.getItemStacks().set(0, Arrays.asList(recipeWrapper.input.getMatchingStacks()));
	}

	public static class Wrapper implements IRecipeWrapper {
		private final Ingredient input;
		private final String name;

		public Wrapper(Brew recipe) {
			input = recipe.input;
			name = I18n.format(recipe.effect.getEffectName());
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(input.getMatchingStacks()));
		}

		@Override
		public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
			FontRenderer font = minecraft.fontRenderer;
			font.drawString(name, ((width - font.getStringWidth(name)) / 2), 25, 0);
		}
	}
}