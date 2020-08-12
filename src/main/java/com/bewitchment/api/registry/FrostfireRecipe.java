package com.bewitchment.api.registry;

import lombok.Data;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Data
public class FrostfireRecipe extends IForgeRegistryEntry.Impl<FrostfireRecipe> {
    private final Ingredient input;
    private final ItemStack output;

    public FrostfireRecipe(ResourceLocation name, Ingredient input, ItemStack output) {
        setRegistryName(name);
        this.input = input;
        this.output = output;
    }

    public final boolean matches(ItemStack input) {
        for (ItemStack stack : this.input.getMatchingStacks())
            if (OreDictionary.itemMatches(stack, input, true)) return true;
        return false;
    }
}