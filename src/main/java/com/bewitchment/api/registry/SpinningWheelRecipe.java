package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class SpinningWheelRecipe extends IForgeRegistryEntry.Impl<SpinningWheelRecipe> {
    public final List<Ingredient> input;
    public final List<ItemStack> output;

    public SpinningWheelRecipe(ResourceLocation name, List<Ingredient> input, List<ItemStack> output) {
        if (input.size() > 4)
            throw new IllegalArgumentException("Input size for " + name.toString() + " is too big, must be 4 at most.");
        if (output.size() > 2)
            throw new IllegalArgumentException("Output size for " + name.toString() + " is too big, must be 2 at most.");
        setRegistryName(name);
        this.input = input;
        this.output = output;
    }

    public final boolean matches(ItemStackHandler input) {
        return Util.areISListsEqual(this.input, input);
    }

    public final boolean isValid(ItemStackHandler output) {
        return Util.canMerge(output.getStackInSlot(0), this.output.get(0)) && ((this.output.size() < 2) || Util.canMerge(output.getStackInSlot(1), this.output.get(1)));
    }

    public final void giveOutput(ItemStackHandler input, ItemStackHandler output) {
        for (int i = 0; i < input.getSlots(); i++) input.extractItem(i, 1, false);
        for (ItemStack stack : this.output)
            output.insertItem(ModTileEntity.getFirstValidSlot(output, stack), stack.copy(), false);
    }
}