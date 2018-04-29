package com.bewitchment.client.jei.components;

import com.bewitchment.common.item.magic.brew.ItemBrew;
import com.bewitchment.common.item.magic.brew.ItemBrewArrow;
import com.bewitchment.common.lib.LibMod;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus.Mode;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BrewingCategory implements IRecipeCategory<BrewingWrapper> {
	
	public static IDrawable bg;
	
	public static final String UID = LibMod.MOD_ID + ":cauldron";
	
	public BrewingCategory(IGuiHelper igh) {
		ResourceLocation rl = new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_brewing.png");
		bg = igh.createDrawable(rl, 0, 0, 91, 40, 91, 40);
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.brewing");
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
	public void setRecipe(IRecipeLayout l, BrewingWrapper w, IIngredients ingredients) {
		IGuiItemStackGroup is = l.getItemStacks();
		
		is.init(2, true, 45, 0);
		
		if (l.getFocus() != null) {
			if (l.getFocus().getValue() instanceof ItemStack && l.getFocus().getMode() == Mode.OUTPUT) {
				if (((ItemStack) l.getFocus().getValue()).getItem() instanceof ItemBrewArrow) {
					is.set(2, new ItemStack(Items.ARROW));
				} else if (((ItemStack) l.getFocus().getValue()).getItem() instanceof ItemBrew) {
					is.set(2, new ItemStack(Items.GLASS_BOTTLE)); // TODO change when bottles change
				}
			}
			
		}
		
		is.init(0, true, 18, 0);
		is.set(0, ingredients.getInputs(ItemStack.class).get(0));
		
		is.init(1, false, 62, 19);
		is.set(1, ingredients.getOutputs(ItemStack.class).get(0));
	}
}
