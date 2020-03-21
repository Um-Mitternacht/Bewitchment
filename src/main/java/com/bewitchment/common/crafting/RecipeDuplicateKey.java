package com.bewitchment.common.crafting;

import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.registry.ModObjects;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeDuplicateKey extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean hasKey = false;
		boolean hasWood = false;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!hasKey && stack.getItem() instanceof ItemJuniperKey) { //so there can only be ONE
				hasKey = true;
			}
			else if (stack.getItem() instanceof ItemJuniperKey) {
				return false;
			}
			if (!hasWood && stack.getItem() == ItemBlock.getItemFromBlock(ModObjects.juniper_planks)) { //so there can only be ONE
				hasWood = true;
			}
			else if (stack.getItem() == ItemBlock.getItemFromBlock(ModObjects.juniper_planks)) {
				return false;
			}
		}
		return hasKey && hasWood;
	}
	
	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack key = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getItem() instanceof ItemJuniperKey) {
				key = stack.copy();
				key.setCount(1);
			}
		}
		
		return key;
	}
	
	public boolean canFit(int width, int height) {
		return width * height >= 9;
	}
	
	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		for (int i = 0; i < nonnulllist.size(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() instanceof ItemJuniperKey) {
					ItemStack stack = itemstack.copy();
					stack.setCount(1);
					nonnulllist.set(i, stack);
				}
				else {
					nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
				}
			}
		}
		System.out.println(nonnulllist);
		return nonnulllist;
	}
	
	public boolean isDynamic() {
		return true;
	}
}
