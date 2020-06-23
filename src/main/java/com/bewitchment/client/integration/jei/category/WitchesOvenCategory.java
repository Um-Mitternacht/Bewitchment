package com.bewitchment.client.integration.jei.category;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.registry.ModObjects;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

@SuppressWarnings("NullableProblems")
public class WitchesOvenCategory implements IRecipeCategory<WitchesOvenCategory.Wrapper> {
    public static final String UID = ModObjects.witches_oven.getTranslationKey() + ".name";

    private final IDrawable bg;

    public WitchesOvenCategory(IGuiHelper helper) {
        bg = helper.drawableBuilder(new ResourceLocation(Bewitchment.MODID, "textures/gui/jei_witches_oven.png"), 0, 0, 82, 54).setTextureSize(82, 54).build();
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
        recipeLayout.getItemStacks().init(0, true, 0, 0);
        recipeLayout.getItemStacks().set(0, recipeWrapper.input);
        recipeLayout.getItemStacks().init(1, false, 60, 4);
        recipeLayout.getItemStacks().set(1, recipeWrapper.output);
        recipeLayout.getItemStacks().init(2, false, 60, 36);
        recipeLayout.getItemStacks().set(2, recipeWrapper.byproduct);
    }

    public static class Wrapper implements IRecipeWrapper {
        private final ItemStack input;
        private final ItemStack output;
        private final ItemStack byproduct;

        public Wrapper(OvenRecipe recipe) {
            input = recipe.input;
            output = recipe.output;
            byproduct = recipe.byproduct;
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setInput(VanillaTypes.ITEM, input);
            ingredients.setOutputs(VanillaTypes.ITEM, Arrays.asList(output, byproduct));
        }
    }
}