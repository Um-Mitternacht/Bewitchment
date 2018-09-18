package com.bewitchment.common.crafting;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OvenSmeltingRecipe extends IForgeRegistryEntry.Impl<OvenSmeltingRecipe> {
	private static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(LibMod.MOD_ID, "oven");
	public static final IForgeRegistry<OvenSmeltingRecipe> REGISTRY = new RegistryBuilder<OvenSmeltingRecipe>().disableSaving().setName(REGISTRY_LOCATION).setType(OvenSmeltingRecipe.class).setIDRange(0, 200).create();

	private Ingredient input;
	private ItemStack output, fumes;
	private float fumeChance;

	/**
	 * @param regName    The resourceLocation name of this entry in the forge registry with the format "mod:regName". Cannot be null.
	 * @param input      The input needed for this recipe. Cannot be null.
	 * @param output     The output that will be produced by this recipe. Cannot be null.
	 * @param fumes      The stack created as a byproduct of smelting the input. If null the recipe produces no fumes.
	 * @param fumeChance The chance of obtaining the byproduct. Must be between 0 and 1;
	 */
	public OvenSmeltingRecipe(ResourceLocation regName, Ingredient input, ItemStack output, @Nonnull ItemStack fumes, float fumeChance) {
		if (fumeChance < 0 || fumeChance > 1) {
			throw new IllegalArgumentException("fumeChance must be between 0 and 1");
		}
		if (fumes == null) {
			throw new NullPointerException("Fumes cannot be null, recipe: " + regName);
		}
		this.setRegistryName(regName);
		this.input = input;
		this.output = output;
		this.fumes = fumes;
		this.fumeChance = fumeChance;
	}

	/**
	 * Determines if there are any registry entries that contains input as the stack to be smelted.
	 *
	 * @param input The stack to be tested.
	 * @return weather their is a recipe in the registry that requires input to be smelted.
	 */
	public static boolean isSmeltable(ItemStack input) {
		for (OvenSmeltingRecipe recipe : REGISTRY) {
			for (ItemStack stack : recipe.getInput().getMatchingStacks()) {
				if (ItemStack.areItemsEqual(stack, input)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Finds any registry entries that contains input as the stack to be smelted.
	 *
	 * @param input The stack to be tested
	 * @return The recipe that contains input as the stack to be smelted. Returns null if not recipe is found.
	 */
	@Nullable
	public static OvenSmeltingRecipe getRecipe(ItemStack input) {
		for (OvenSmeltingRecipe recipe : REGISTRY) {
			if (recipe.getInput().apply(input)) {
				return recipe;
			}
		}

		return null;
	}

	/**
	 * @return the input ingredient.
	 */
	public Ingredient getInput() {
		return input;
	}

	/**
	 * @return a copy of the output stack.
	 */
	public ItemStack getOutput() {
		return output.copy();
	}

	/**
	 * @return a copy of the fume stack. If returns null then recipe has no fumes.
	 */
	@Nullable
	public ItemStack getFumes() {
		return fumes.copy();
	}

	/**
	 * @return the chance of producing fumes.
	 */
	public float getFumeChance() {
		return fumeChance;
	}
}
