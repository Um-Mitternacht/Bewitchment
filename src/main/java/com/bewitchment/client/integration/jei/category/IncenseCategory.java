package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Incense;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class IncenseCategory implements IRecipeCategory<IncenseCategory.Wrapper> {
	public static final String UID = "jei.incense";

	private final IDrawable bg;

	public IncenseCategory(IGuiHelper helper) {
		bg = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_brazier.png"), 0, 0, 72, 57).setTextureSize(72, 57).build();
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
			recipeLayout.getItemStacks().init(i, true, (i % 4) * 18, (i / 4) * 18);
			recipeLayout.getItemStacks().set(i, Arrays.asList(recipeWrapper.input.get(i).getMatchingStacks()));
		}
	}

	public static class Wrapper implements IRecipeWrapper {
		private final List<Ingredient> input;
		private final String name;

		public Wrapper(Incense incense) {
			input = incense.getInput();
			name = new TextComponentTranslation("incense." + incense.getRegistryName().toString().replace(":", ".")).getFormattedText();
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<List<ItemStack>> lists = new ArrayList<>();
			for (Ingredient ing : input) lists.add(Arrays.asList(ing.getMatchingStacks()));
			ingredients.setInputLists(VanillaTypes.ITEM, lists);
		}

		@Override
		public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
			FontRenderer font = minecraft.fontRenderer;
			font.drawString(name, ((width - font.getStringWidth(name)) / 2), 47, 0);
		}
	}
}
