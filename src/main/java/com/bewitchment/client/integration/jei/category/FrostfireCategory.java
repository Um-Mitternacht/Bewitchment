package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.FrostfireRecipe;
import com.bewitchment.registry.ModObjects;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

@SuppressWarnings("NullableProblems")
public class FrostfireCategory implements IRecipeCategory<FrostfireCategory.Wrapper> {
	public static final String UID = ModObjects.frostfire.getTranslationKey() + ".name";

	private final IDrawable bg;

	public FrostfireCategory(IGuiHelper helper) {
		bg = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_frostfire.png"), 0, 0, 64, 24).setTextureSize(64, 24).build();
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
		recipeLayout.getItemStacks().init(0, false, 4, 3);
		recipeLayout.getItemStacks().set(0, Arrays.asList(recipeWrapper.input.getMatchingStacks()));

		recipeLayout.getItemStacks().init(1, false, 42, 3);
		recipeLayout.getItemStacks().set(1, recipeWrapper.output);
	}

	public static class Wrapper implements IRecipeWrapper {
		private final Ingredient input;
		private final ItemStack output;

		public Wrapper(FrostfireRecipe recipe) {
			input = recipe.getInput();
			output = recipe.getOutput();
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(input.getMatchingStacks()));
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
	}
}