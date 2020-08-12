package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.SigilRecipe;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SigilCategory implements IRecipeCategory<SigilCategory.Wrapper> {
	public static final String UID = ModObjects.sigil_table.getTranslationKey() + ".name";
	private final IDrawable bg;

	public SigilCategory(IGuiHelper helper) {
		bg = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_sigil_table.png"), 0, 0, 127, 90).setTextureSize(127, 90).build();
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
			recipeLayout.getItemStacks().init(i + 1, true, (i % 5) * 18, (i / 5) * 18);
			recipeLayout.getItemStacks().set(i + 1, Arrays.asList(recipeWrapper.input.get(i).getMatchingStacks()));
		}
		recipeLayout.getItemStacks().init(0, false, 109, 36);
		recipeLayout.getItemStacks().set(0, recipeWrapper.output);
	}

	public static class Wrapper implements IRecipeWrapper {
		private final List<Ingredient> input;
		private final ItemStack output;

		public Wrapper(SigilRecipe recipe) {
			input = recipe.getInput();
			output = recipe.getOutput();
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<List<ItemStack>> lists = new ArrayList<>();
			for (Ingredient ing : input) lists.add(Arrays.asList(ing.getMatchingStacks()));
			ingredients.setInputLists(VanillaTypes.ITEM, lists);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
	}
}
