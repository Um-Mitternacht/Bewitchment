package com.bewitchment.client.jei.components;

import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.lib.LibMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RitualWrapper implements IRecipeWrapper {
	private static IDrawable centerGlyph, circle1, circle2, circle3;
	List<List<ItemStack>> input;
	List<ItemStack> output;
	private int circles, powerStart, powerTick;
	private String name;

	public RitualWrapper(AdapterIRitual ritual, IGuiHelper igh) {
		output = ritual.getOutputRaw();
		input = ritual.getJeiInput();
		circles = ritual.getCircles();
		powerStart = ritual.getRequiredStartingPower();
		powerTick = ritual.getRunningPower();
		name = I18n.format("ritual." + ritual.getRegistryName().toString().replace(':', '.') + ".name");
		if (centerGlyph == null) {
			centerGlyph = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_ritual_0.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
			circle1 = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_ritual_1.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
			circle2 = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_ritual_2.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
			circle3 = igh.drawableBuilder(new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_ritual_3.png"), 0, 0, 34, 34).setTextureSize(34, 34).build();
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		if (!output.isEmpty()) {
			ingredients.setOutputs(VanillaTypes.ITEM, output);
		}
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FontRenderer fr = minecraft.fontRenderer;
		String powerFlatDesc = I18n.format("jei.ritual.power.flat", powerStart);
		String powerTickDesc = I18n.format("jei.ritual.power.tick", powerTick * 20);
		int mult = (int) (powerTick > 0 ? 3.1 : 2);
		if (powerStart > 0)
			fr.drawString(powerFlatDesc, (recipeWidth - fr.getStringWidth(powerFlatDesc)) / 2, recipeHeight - mult * fr.FONT_HEIGHT, 0, false);
		if (powerTick > 0)
			fr.drawString(powerTickDesc, (recipeWidth - fr.getStringWidth(powerTickDesc)) / 2, recipeHeight - 2 * fr.FONT_HEIGHT, 0, false);
		fr.drawString(name, (recipeWidth - fr.getStringWidth(name)) / 2, 0, 0);


		int requiredCircles = circles & 3;
		EnumGlyphType typeFirst = EnumGlyphType.fromMeta(circles >> 2 & 3);
		EnumGlyphType typeSecond = EnumGlyphType.fromMeta(circles >> 4 & 3);
		EnumGlyphType typeThird = EnumGlyphType.fromMeta(circles >> 6 & 3);
		color(EnumGlyphType.GOLDEN, null, 0);

		int dx = 53, dy = 35;

		centerGlyph.draw(minecraft, dx, dy);
		color(typeFirst, minecraft, 0);
		circle1.draw(minecraft, dx, dy);
		if (requiredCircles > 0) {
			color(typeSecond, minecraft, 1);
			circle2.draw(minecraft, dx, dy);
		}
		if (requiredCircles > 1) {
			color(typeThird, minecraft, 2);
			circle3.draw(minecraft, dx, dy);
		}
	}

	private void color(EnumGlyphType gt, Minecraft minecraft, int circle) {
		switch (gt) {
			case ENDER:
				GlStateManager.color(0.5f, 0f, 0.5f);
				break;
			case GOLDEN:
				GlStateManager.color(1f, 1f, 0f);
				break;
			case NETHER:
				GlStateManager.color(0.8f, 0f, 0f);
				break;
			case NORMAL:
				GlStateManager.color(0.9f, 0.9f, 0.9f);
				break;
			case ANY:
				colorRandom(minecraft.world.getTotalWorldTime(), circle);
				break;
			default:
				Bewitchment.logger.warn("Probable bug in Bewitchment [RitualWrapper.java]");
				break;
		}
	}

	private void colorRandom(long v, int circle) {
		int r = (int) (v % 60 / 20);
		switch ((r + circle) % 3) {
			case 1:
				color(EnumGlyphType.NORMAL, null, 0);
				break;
			case 2:
				color(EnumGlyphType.ENDER, null, 0);
				break;
			case 0:
				color(EnumGlyphType.NETHER, null, 0);
				break;
			default:
				break;
		}
	}


}
