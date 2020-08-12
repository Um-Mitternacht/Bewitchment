package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.client.integration.jei.category.RitualCategory.Wrapper;
import com.bewitchment.common.block.BlockGlyph;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class RitualCategory implements IRecipeCategory<Wrapper> {
	public static final String UID = "jei.ritual";

	private final IDrawable bg;

	public RitualCategory(IGuiHelper helper) {
		bg = helper.createBlankDrawable(180, 110);
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
			recipeLayout.getItemStacks().init(i, true, 18 * i + (180 - 18 * recipeWrapper.input.size()) / 2, 10);
			recipeLayout.getItemStacks().set(i, recipeWrapper.input.get(i));
		}
		if (recipeWrapper.output != null) {
			for (int i = 0; i < recipeWrapper.output.size(); i++) {
				recipeLayout.getItemStacks().init(i + recipeWrapper.input.size(), false, 18 * i + (180 - 18 * recipeWrapper.output.size()) / 2, 68);
				recipeLayout.getItemStacks().set(i + recipeWrapper.input.size(), recipeWrapper.output.get(i));
			}
		}
	}

	@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
	public static class Wrapper implements IRecipeWrapper {
		private static IDrawable center, small, medium, large;

		private final int[] circles;
		private final List<List<ItemStack>> input;
		private final List<ItemStack> output;
		private final int startingPower;
		private final int runningPower;
		private final String name;

		public Wrapper(Ritual ritual, IGuiHelper helper) {
			circles = ritual.getCircles();
			input = new ArrayList<>();
			for (Ingredient ing : ritual.getInput()) input.add(Arrays.asList(ing.getMatchingStacks()));
			output = ritual.getOutput();
			startingPower = ritual.getStartingPower();
			runningPower = ritual.getRunningPower();
			name = I18n.format("ritual." + ritual.getRegistryName().toString().replace(":", "."));
			center = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_ritual_0.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
			small = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_ritual_1.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
			medium = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_ritual_2.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
			large = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_ritual_3.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, input);
			if (output != null && !output.isEmpty()) ingredients.setOutputs(VanillaTypes.ITEM, output);
		}

		@Override
		public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
			FontRenderer font = minecraft.fontRenderer;
			font.drawString(name, ((width - font.getStringWidth(name)) / 2), 0, 0);
			String startingPower = I18n.format("jei.ritual.startingPower", this.startingPower), runningPower = I18n.format("jei.ritual.runningPower", this.runningPower);
			if (this.startingPower > 0)
				font.drawString(startingPower, (width - font.getStringWidth(startingPower)) / 2, height - (this.runningPower > 0 ? 3 : 2) * font.FONT_HEIGHT * 4 / 5, 0);
			if (this.runningPower > 0)
				font.drawString(runningPower, (width - font.getStringWidth(runningPower)) / 2, height - 2 * font.FONT_HEIGHT * 2 / 3, 0);
			int x = 73, y = 24;
			color(minecraft, BlockGlyph.GOLDEN);
			center.draw(minecraft, x, y * 5 / 4);
			color(minecraft, circles[0]);
			small.draw(minecraft, x, y * 5 / 4);
			if (circles[1] > 0) {
				color(minecraft, circles[1]);
				medium.draw(minecraft, x, y * 5 / 4);
				if (circles[2] > 0) {
					color(minecraft, circles[2]);
					large.draw(minecraft, x, y * 5 / 4);
				}
			}
		}

		private void color(Minecraft minecraft, int type) {
			if (type == BlockGlyph.GOLDEN) GL11.glColor3f(1, 1, 0);
			else if (type == BlockGlyph.NORMAL) GL11.glColor3f(0.9f, 0.9f, 0.9f);
			else if (type == BlockGlyph.NETHER) GL11.glColor3f(0.8f, 0, 0);
			else if (type == BlockGlyph.ENDER) GL11.glColor3f(0.5f, 0, 0.5f);
			else {
				int r = (int) (minecraft.world.getTotalWorldTime() % 60 / 20);
				if (r == 0) color(minecraft, BlockGlyph.NORMAL);
				else if (r == 1) color(minecraft, BlockGlyph.NETHER);
				else color(minecraft, BlockGlyph.ENDER);
			}
		}
	}

	public static class RitualWrapperFactory implements IRecipeWrapperFactory<Ritual> {
		private final IGuiHelper helper;

		public RitualWrapperFactory(IGuiHelper helper) {
			this.helper = helper;
		}

		@Override
		public IRecipeWrapper getRecipeWrapper(Ritual ritual) {
			return new Wrapper(ritual, helper);
		}
	}
}