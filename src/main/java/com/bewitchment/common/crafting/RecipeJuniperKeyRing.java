package com.bewitchment.common.crafting;

import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.common.item.tool.ItemJuniperKeyRing;
import com.bewitchment.registry.ModObjects;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeJuniperKeyRing extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() instanceof ItemJuniperKey) {
                list.add(stack);
            } else if (!stack.isEmpty()) {
                return false;
            }
        }
        return list.size() > 1;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Set<NBTTagCompound> tags = new HashSet<>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() instanceof ItemJuniperKey) {
                if (stack.hasTagCompound()) {
                    if (stack.getItem() instanceof ItemJuniperKeyRing)
                        tags.addAll(ItemJuniperKeyRing.getKeyTags(stack.getTagCompound()));
                    else if (stack.getItem() instanceof ItemJuniperKey) tags.add(stack.getTagCompound());
                }
            }
        }
        ItemStack itemstack = ItemJuniperKeyRing.createKeyRing(new ItemStack(ModObjects.juniper_key_ring), tags);
        return itemstack;
    }

    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModObjects.juniper_key_ring);
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
            }
        }
        return nonnulllist;
    }

    public boolean isDynamic() {
        return true;
    }
}
