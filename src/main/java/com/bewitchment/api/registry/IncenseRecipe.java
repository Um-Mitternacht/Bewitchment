package com.bewitchment.api.registry;

import com.bewitchment.Util;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class IncenseRecipe extends IForgeRegistryEntry.Impl<IncenseRecipe> {
    public final List<Ingredient> input;
    public final List<PotionEffect> incense;

    public IncenseRecipe(ResourceLocation name, List<Ingredient> input, List<PotionEffect> incense) {
        setRegistryName(name);
        this.input = input;
        this.incense = incense;
    }

    public final boolean matches(ItemStackHandler input) {
        return Util.areISListsEqual(this.input, input);
    }
}
