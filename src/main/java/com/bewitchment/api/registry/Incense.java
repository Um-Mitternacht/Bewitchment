package com.bewitchment.api.registry;

import com.bewitchment.Util;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class Incense extends IForgeRegistryEntry.Impl<Incense> {
    public final List<Ingredient> input;
    public final List<Potion> effects;
    public final int time;

    public Incense(ResourceLocation name, List<Ingredient> input, List<Potion> effects, int time) {
        if (input.size() > 8)
            throw new IllegalArgumentException("Input size for " + name.toString() + " is too big, must be 8 at most.");
        setRegistryName(name);
        this.effects = effects;
        this.input = input;
        this.time = time;
    }

    public final boolean matches(ItemStackHandler input) {
        return Util.areISListsEqual(this.input, input);
    }
}
