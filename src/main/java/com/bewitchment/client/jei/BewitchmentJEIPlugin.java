package com.bewitchment.client.jei;

import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.client.jei.components.*;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.CauldronCraftingRecipe;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.crafting.OvenSmeltingRecipe;
import com.bewitchment.common.crafting.SpinningThreadRecipe;
import com.bewitchment.common.item.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

import java.util.stream.Collectors;

@JEIPlugin
public class BewitchmentJEIPlugin implements IModPlugin {
	protected static int compareRituals(AdapterIRitual a, AdapterIRitual b) {
		if (a == b)
			return 0;
		int av = a.getInput().size() / 3;
		int bv = b.getInput().size() / 3;
		av += a.getCircles() & 3;
		bv += b.getCircles() & 3;
		return av > bv ? 1 : -1;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RitualCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new SpinnerCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new OvenCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new BrewingCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new BrewModifierCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new CauldronCraftingCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(AdapterIRitual.class, new RitualWrapperFactory(registry.getJeiHelpers().getGuiHelper()), RitualCategory.UID);
		registry.addRecipes(AdapterIRitual.REGISTRY.getValuesCollection().stream().sorted(BewitchmentJEIPlugin::compareRituals).collect(Collectors.toList()), RitualCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.ritual_chalk, 1, EnumGlyphType.GOLDEN.ordinal()), RitualCategory.UID);

		registry.handleRecipes(SpinningThreadRecipe.class, i -> new SpinnerWrapper(i), SpinnerCategory.UID);
		registry.addRecipes(SpinningThreadRecipe.REGISTRY.getValuesCollection(), SpinnerCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.thread_spinner), SpinnerCategory.UID);

		registry.handleRecipes(OvenSmeltingRecipe.class, i -> new OvenWrapper(i), OvenCategory.UID);
		registry.addRecipes(OvenSmeltingRecipe.REGISTRY.getValuesCollection(), OvenCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.oven), OvenCategory.UID);

		registry.handleRecipes(IBrewEffect.class, BrewingWrapper::new, BrewingCategory.UID);
		registry.addRecipes(CauldronRegistry.BREW_POTION_MAP.keySet(), BrewingCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.cauldron), BrewingCategory.UID);

		registry.handleRecipes(IBrewModifier.class, BrewModifierWrapper::new, BrewModifierCategory.UID);
		registry.addRecipes(CauldronRegistry.BREW_MODIFIERS.getValuesCollection(), BrewModifierCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.cauldron), BrewModifierCategory.UID);

		registry.handleRecipes(CauldronCraftingRecipe.class, CauldronCraftingWrapper::new, CauldronCraftingCategory.UID);
		registry.addRecipes(CauldronRegistry.CRAFTING_REGISTRY, CauldronCraftingCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.cauldron), CauldronCraftingCategory.UID);
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

		ISubtypeInterpreter brewDataInterpreter = new BrewDataInterpreter();

		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_phial_drink, brewDataInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_phial_linger, brewDataInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_phial_splash, brewDataInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_arrow, brewDataInterpreter);
	}

	private static final class BrewDataInterpreter implements ISubtypeInterpreter {

		@Override
		public String apply(ItemStack itemStack) {
			BrewData data = BrewData.fromStack(itemStack);
			if (data.getEffects().isEmpty()) {
				return NONE;
			}
			return data.getEffects().get(0).getPotion().getRegistryName().toString();
		}
	}

	protected static class RitualWrapperFactory implements IRecipeWrapperFactory<AdapterIRitual> {

		private IGuiHelper igh;

		public RitualWrapperFactory(IGuiHelper igh) {
			this.igh = igh;
		}

		@Override
		public IRecipeWrapper getRecipeWrapper(AdapterIRitual recipe) {
			return new RitualWrapper(recipe, igh);
		}
	}
}
