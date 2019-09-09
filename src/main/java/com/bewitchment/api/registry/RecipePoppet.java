package com.bewitchment.api.registry;

import com.bewitchment.common.item.ItemPoppet;
import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RecipePoppet extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> items = new ArrayList<>();
		for (int i = 0; i < inv.getWidth(); ++i) {
			for (int j = 0; j < inv.getHeight(); ++j) {
				ItemStack itemstack = inv.getStackInRowAndColumn(i, j);
				if (!itemstack.isEmpty()) items.add(itemstack);
			}
		}
		for (ItemStack item : items) {
			if (!(item.getItem() instanceof ItemTaglock || item.getItem() instanceof ItemPoppet)) return false;
			if (item.getItem() instanceof ItemTaglock && !item.hasTagCompound()) return false;
			if (item.getItem() instanceof ItemPoppet && item.hasTagCompound()) return false;
		}
		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack taglock = null, poppet = null;
		for (int i = 0; i < inv.getWidth(); ++i) {
			for (int j = 0; j < inv.getHeight(); ++j) {
				ItemStack itemstack = inv.getStackInRowAndColumn(i, j);
				if (itemstack.getItem() instanceof ItemTaglock) taglock = itemstack;
				if (itemstack.getItem() instanceof ItemPoppet) poppet = itemstack;
			}
		}
		if (poppet != null && taglock != null) {
			ItemStack returnItem = poppet.copy();
			returnItem.setTagCompound(new NBTTagCompound());
			returnItem.getTagCompound().setString("boundId", taglock.getTagCompound().getString("boundId"));
			returnItem.getTagCompound().setString("boundName", taglock.getTagCompound().getString("boundName"));
			return returnItem;
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
	}

	/**
	 * If true, this recipe does not appear in the recipe book and does not respect recipe unlocking (and the
	 * doLimitedCrafting gamerule)
	 */
	public boolean isDynamic() {
		return true;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}
}