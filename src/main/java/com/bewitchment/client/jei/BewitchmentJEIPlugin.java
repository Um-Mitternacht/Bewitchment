package com.bewitchment.client.jei;

import com.bewitchment.api.ritual.Ritual;
import com.bewitchment.client.jei.components.*;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.spinning.SpinningThreadRecipe;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class BewitchmentJEIPlugin implements IModPlugin {
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RitualCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new SpinnerCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(Ritual.class, new RitualWrapperFactory(registry.getJeiHelpers().getGuiHelper()), RitualCategory.UID);
		registry.addRecipes(Ritual.REGISTRY.getValues(), RitualCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.ritual_chalk, 1, BlockCircleGlyph.GlyphType.GOLDEN.ordinal()), RitualCategory.UID);
		
		registry.handleRecipes(SpinningThreadRecipe.class, i -> new SpinnerWrapper(i), SpinnerCategory.UID);
		registry.addRecipes(SpinningThreadRecipe.REGISTRY.getValues(), SpinnerCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.thread_spinner), SpinnerCategory.UID);
	}
	
	protected static class RitualWrapperFactory implements IRecipeWrapperFactory<Ritual> {
		
		private IGuiHelper igh;
		
		public RitualWrapperFactory(IGuiHelper igh) {
			this.igh = igh;
		}
		
		@Override
		public IRecipeWrapper getRecipeWrapper(Ritual recipe) {
			return new RitualWrapper(recipe, igh);
		}
	}
}
