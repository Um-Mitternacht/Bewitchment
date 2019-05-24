package com.bewitchment.client.integration.jei;

import com.bewitchment.api.registry.DistilleryRecipe;
import com.bewitchment.api.registry.FrostfireRecipe;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.api.registry.SpinningWheelRecipe;
import com.bewitchment.client.integration.jei.category.DistilleryCategory;
import com.bewitchment.client.integration.jei.category.FrostfireCategory;
import com.bewitchment.client.integration.jei.category.SpinningWheelCategory;
import com.bewitchment.client.integration.jei.category.WitchesOvenCategory;
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
		registry.addRecipeCategories(new FrostfireCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new WitchesOvenCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new DistilleryCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new SpinningWheelCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(FrostfireRecipe.class, FrostfireCategory.FrostfireWrapper::new, FrostfireCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(FrostfireRecipe.class).getValuesCollection(), FrostfireCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.frostfire), FrostfireCategory.UID);
		
		registry.handleRecipes(OvenRecipe.class, WitchesOvenCategory.WitchesOvenWrapper::new, WitchesOvenCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection(), WitchesOvenCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.witches_oven), WitchesOvenCategory.UID);
		
		registry.handleRecipes(DistilleryRecipe.class, DistilleryCategory.DistilleryWrapper::new, DistilleryCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(DistilleryRecipe.class).getValuesCollection(), DistilleryCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.distillery), DistilleryCategory.UID);
		
		registry.handleRecipes(SpinningWheelRecipe.class, SpinningWheelCategory.SpinningWheelWrapper::new, SpinningWheelCategory.UID);
		registry.addRecipes(GameRegistry.findRegistry(SpinningWheelRecipe.class).getValuesCollection(), SpinningWheelCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModObjects.spinning_wheel), SpinningWheelCategory.UID);
	}
}