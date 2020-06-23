package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public class RecipeWand extends ShapelessRecipes {
    public RecipeWand() {
        super("bewitchment:leonards_wand", new ItemStack(ModObjects.leonards_wand), NonNullList.from(Ingredient.EMPTY, Util.get(ModObjects.leonards_wand), Util.get(Items.SPLASH_POTION)));
    }

    @NotNull
    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModObjects.leonards_wand);
    }

    @NotNull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
        ItemStack wand = ItemStack.EMPTY;

        for (int i = 0; i < var1.getSizeInventory(); ++i) {
            ItemStack stack = var1.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == ModObjects.leonards_wand) {
                wand = stack;
            }
        }

        if (wand.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack wandCopy = wand.copy();
            for (int i = 0; i < var1.getSizeInventory(); ++i) {
                ItemStack stack = var1.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() == Items.SPLASH_POTION) {
                    wandCopy.setTagCompound(new NBTTagCompound());
                    wandCopy.getTagCompound().setInteger("uses", 16);
                    List<PotionEffect> effects = PotionUtils.getEffectsFromStack(stack);
                    PotionUtils.appendEffects(wandCopy, effects);
                }
            }
            return wandCopy;
        }
    }

    @Override
    public boolean canFit(int i, int i1) {
        return i >= 2 && i1 >= 2;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
