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
        registry.addRecipeCategories(new CauldronCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new BrewCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new WitchesOvenCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new DistilleryCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpinningWheelCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new FrostfireCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new IncenseCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SigilCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(Ritual.class, new RitualCategory.RitualWrapperFactory(registry.getJeiHelpers().getGuiHelper()), RitualCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(Ritual.class).getValuesCollection(), RitualCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.focal_chalk), RitualCategory.UID);

        registry.handleRecipes(CauldronRecipe.class, CauldronCategory.Wrapper::new, CauldronCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(CauldronRecipe.class).getValuesCollection(), CauldronCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.witches_cauldron), CauldronCategory.UID);

        registry.handleRecipes(Brew.class, BrewCategory.Wrapper::new, BrewCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(Brew.class).getValuesCollection(), BrewCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.witches_cauldron), BrewCategory.UID);

        registry.handleRecipes(OvenRecipe.class, WitchesOvenCategory.Wrapper::new, WitchesOvenCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection(), WitchesOvenCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.witches_oven), WitchesOvenCategory.UID);

        registry.handleRecipes(DistilleryRecipe.class, DistilleryCategory.Wrapper::new, DistilleryCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(DistilleryRecipe.class).getValuesCollection(), DistilleryCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.distillery), DistilleryCategory.UID);

        registry.handleRecipes(SpinningWheelRecipe.class, SpinningWheelCategory.Wrapper::new, SpinningWheelCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(SpinningWheelRecipe.class).getValuesCollection(), SpinningWheelCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.spinning_wheel), SpinningWheelCategory.UID);

        registry.handleRecipes(FrostfireRecipe.class, FrostfireCategory.Wrapper::new, FrostfireCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(FrostfireRecipe.class).getValuesCollection(), FrostfireCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.bottled_frostfire), FrostfireCategory.UID);

        registry.handleRecipes(Incense.class, IncenseCategory.Wrapper::new, IncenseCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(Incense.class).getValuesCollection(), IncenseCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.brazier), IncenseCategory.UID);

        registry.handleRecipes(SigilRecipe.class, SigilCategory.Wrapper::new, SigilCategory.UID);
        registry.addRecipes(GameRegistry.findRegistry(SigilRecipe.class).getValuesCollection(), SigilCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModObjects.sigil_table), SigilCategory.UID);
    }
}