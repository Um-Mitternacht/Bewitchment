package com.bewitchment.api.cauldron;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IBrewModifier extends IForgeRegistryEntry<IBrewModifier> {
	
	public static final int PASS = Integer.MAX_VALUE;
	
	/**
	 * Decide if a modifier is applicable to a specific potion
	 * 
	 * @param brew The brew in question
	 * @return true if the modifier can be applied to this potion, false otherwise
	 */
	public boolean canApply(IBrewEffect brew);
	
	/**
	 * Given the ingredient thrown in the cauldron, decide what level this modifier should be set at
	 * 
	 * @param brew The brew being modified
	 * @param stack The stack that should be used as a modifier
	 * @param currentModifiers The current list of modifiers applied to this brew.
	 *            The effect might already be present if the ingredient is thrown in twice,
	 *            so you should check it before in case this uses multiple levels
	 * @return The level at witch this modifier should be set at,
	 *         or {@link #PASS} to ignore this item and let other modifiers process it.
	 */
	public int acceptIngredient(IBrewEffect brew, ItemStack stack, IBrewModifierList currentModifiers);
	
	public Ingredient getJEIStackRepresentative();
	
}
