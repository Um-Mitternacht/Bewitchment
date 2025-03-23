package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.ArrayList;

public class DistilleryRecipe extends IForgeRegistryEntry.Impl<DistilleryRecipe> {
    public final List<Ingredient> input;
    public final List<ItemStack> output;
    private final ArrayList<Integer> outSlots;

    public DistilleryRecipe(ResourceLocation name, List<Ingredient> input, List<ItemStack> output) {
        if (input.size() > 6)
            throw new IllegalArgumentException("Input size for " + name.toString() + " is too big, must be 6 at most.");
        setRegistryName(name);
        this.input = input;
        this.output = output;
        this.outSlots = new ArrayList<Integer>();
    }

    public final boolean matches(ItemStackHandler input) {
        return Util.areISListsEqual(this.input, input);
    }

    public final boolean isValid(ItemStackHandler output) {
        int emptySlotsNeeded = 0;
        outSlots.clear();
        for (ItemStack stack : this.output) {
            int mergeSlot = ModTileEntity.canMerge(output, stack);
            if (mergeSlot == -1) {
                emptySlotsNeeded++;
                outSlots.add(-1);
            } else {
                outSlots.add(mergeSlot);
            }
        }
        if (emptySlotsNeeded != 0) {
            int emptySlotsAvailable = ModTileEntity.getEmptySlots(output);
            return (emptySlotsNeeded <= emptySlotsAvailable);
        }
        return true;
    }

    public final void giveOutput(ItemStackHandler input, ItemStackHandler output) {
        for (int i = 0; i < input.getSlots(); i++)
            input.extractItem(i, 1, false);
        int j = 0;
        for (ItemStack stack : this.output) {

            if (outSlots.get(j) == -1)
                output.insertItem(ModTileEntity.getFirstEmptySlot(output), stack.copy(), false);
            else
                output.insertItem(outSlots.get(j), stack.copy(), false);
            j++;
        }
    }
}
