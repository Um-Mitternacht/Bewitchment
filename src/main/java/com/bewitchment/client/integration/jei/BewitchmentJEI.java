package com.bewitchment.client.integration.jei;

import com.bewitchment.api.registry.DistilleryRecipe;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.api.registry.SpinningWheelRecipe;
import com.bewitchment.client.integration.jei.category.DistilleryCategory;
import com.bewitchment.client.integration.jei.category.OvenCategory;
import com.bewitchment.client.integration.jei.category.SpinningWheelCategory;
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
		registry.addRecipeCategories(new OvenCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new DistilleryCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new SpinningWheelCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(OvenRecipe.class, OvenCategory.OvenWrapper::new, OvenCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection(), OvenCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.oven), OvenCategory.UID);
		
		registry.handleRecipes(DistilleryRecipe.class, DistilleryCategory.DistilleryWrapper::new, DistilleryCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(DistilleryRecipe.class).getValuesCollection(), DistilleryCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.distillery), DistilleryCategory.UID);
		
		registry.handleRecipes(SpinningWheelRecipe.class, SpinningWheelCategory.SpinningWheelWrapper::new, SpinningWheelCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(SpinningWheelRecipe.class).getValuesCollection(), SpinningWheelCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.spinning_wheel), SpinningWheelCategory.UID);
	}
}