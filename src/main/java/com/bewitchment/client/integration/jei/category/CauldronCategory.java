package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.CauldronRecipe;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class CauldronCategory implements IRecipeCategory<CauldronCategory.Wrapper> {
	public static final String UID = "jei.cauldron";
	
	private final IDrawable bg;
	
	public CauldronCategory(IGuiHelper helper) {
		bg = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_cauldron.png"), 0, 0, 160, 64).setTextureSize(160, 64).build();
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
		for (int i = 0; i < recipeWrapper.input.size(); i++) {
			recipeLayout.getItemStacks().init(i, true, 18 * i + (160 - 18 * recipeWrapper.input.size()) / 2, 2);
			recipeLayout.getItemStacks().set(i, recipeWrapper.input.get(i));
		}
		if (recipeWrapper.output != null) {
			for (int i = 0; i < recipeWrapper.output.size(); i++) {
				recipeLayout.getItemStacks().init(i + recipeWrapper.input.size(), false, 18 * i + (160 - 18 * recipeWrapper.output.size()) / 2, 44);
				recipeLayout.getItemStacks().set(i + recipeWrapper.input.size(), recipeWrapper.output.get(i));
			}
		}
	}
	
	public static class Wrapper implements IRecipeWrapper {
		private final List<List<ItemStack>> input;
		private final List<ItemStack> output;
		
		public Wrapper(CauldronRecipe recipe) {
			input = new ArrayList<>();
			for (Ingredient ing : recipe.input) input.add(Arrays.asList(ing.getMatchingStacks()));
			output = recipe.output;
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, input);
			if (output != null && !output.isEmpty()) ingredients.setOutputs(VanillaTypes.ITEM, output);
		}
	}
}