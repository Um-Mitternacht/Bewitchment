package com.bewitchment.api.registry;

import lombok.Value;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Predicate;

@Value
public class Brew extends IForgeRegistryEntry.Impl<Brew> {
    private final Ingredient input;
    private final Predicate<ItemStack> outputPredicate;
    private final ItemStack output;
    private final PotionEffect effect;

    public Brew(ResourceLocation name, Ingredient input, Predicate<ItemStack> outputPredicate, ItemStack output, PotionEffect effect) {
        setRegistryName(name);
        this.input = input;
        this.outputPredicate = outputPredicate;
        this.output = output;
        this.effect = effect;
    }

    public Brew(ResourceLocation name, Ingredient input, ItemStack output, PotionEffect effect) {
        this(name, input, null, output, effect);
    }

    public Brew(ResourceLocation name, Ingredient input, PotionEffect effect) {
        this(name, input, ItemStack.EMPTY, effect);
    }

    public final boolean matches(ItemStack input) {
        for (ItemStack stack : this.input.getMatchingStacks())
            if (OreDictionary.itemMatches(stack, input, true)) return true;
        return false;
    }
}