package com.bewitchment.client.integration.jei;

import com.bewitchment.api.registry.*;
import com.bewitchment.client.integration.jei.category.*;
import com.bewitchment.registry.ModObjects;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("unused")
@JEIPlugin
public class BewitchmentJEI implements IModPlugin {
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RitualCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new WitchesOvenCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new DistilleryCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new SpinningWheelCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new FrostfireCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(Ritual.class, new RitualCategory.RitualWrapperFactory(registry.getJeiHelpers().getGuiHelper()), RitualCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(Ritual.class).getValuesCollection(), RitualCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.focal_chalk), RitualCategory.UID);
		
		registry.handleRecipes(OvenRecipe.class, WitchesOvenCategory.WitchesOvenWrapper::new, WitchesOvenCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection(), WitchesOvenCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.witches_oven), WitchesOvenCategory.UID);
		
		registry.handleRecipes(DistilleryRecipe.class, DistilleryCategory.DistilleryWrapper::new, DistilleryCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(DistilleryRecipe.class).getValuesCollection(), DistilleryCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.distillery), DistilleryCategory.UID);
		
		registry.handleRecipes(SpinningWheelRecipe.class, SpinningWheelCategory.SpinningWheelWrapper::new, SpinningWheelCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(SpinningWheelRecipe.class).getValuesCollection(), SpinningWheelCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.spinning_wheel), SpinningWheelCategory.UID);
		
		registry.handleRecipes(FrostfireRecipe.class, FrostfireCategory.FrostfireWrapper::new, FrostfireCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(FrostfireRecipe.class).getValuesCollection(), FrostfireCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.bottled_frostfire), FrostfireCategory.UID);
	}
}