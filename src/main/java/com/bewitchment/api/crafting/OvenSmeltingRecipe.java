package com.bewitchment.api.crafting;

import com.bewitchment.common.lib.LibMod;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OvenSmeltingRecipe extends IForgeRegistryEntry.Impl<OvenSmeltingRecipe> {
	private static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(LibMod.MOD_ID, "oven");
	public static final IForgeRegistry<OvenSmeltingRecipe> REGISTRY = new RegistryBuilder<OvenSmeltingRecipe>().disableSaving().setName(REGISTRY_LOCATION).setType(OvenSmeltingRecipe.class).setIDRange(0, 200).create();

	private Ingredient input;
	private ItemStack output, fumes;
	private int fumeChance;

	/**
	 * @param regName The name of this entry in the forge registry with the format "mod:regName". Cannot be null.
	 * @param input The input needed for this recipe. Cannot be null.
	 * @param output The output that will be produced by this recipe.  Cannot be null.
	 * @param fumes The stack created as a byproduct of smelting the input. If null the recipe produces no fumes.
	 * @param fumeChance The chance of obtaining the byproduct. Must be between 0 and 100.
	 */
	public OvenSmeltingRecipe(String regName, Ingredient input, ItemStack output, @Nullable ItemStack fumes, int fumeChance) {
		if(fumeChance > 100 || fumeChance < 0) { throw new IllegalArgumentException("fumeChance must be between 0 and 100. "); }
		this.setRegistryName(new ResourceLocation(LibMod.MOD_ID, regName));
		this.input = input;
		this.output = output;
		this.fumes = fumes;
		this.fumeChance = fumeChance;
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
		if(fumes == null) {
			return null;
		}
		return fumes.copy();
	}

	/**
	 * @return the chance of producing fumes.
	 */
	public int getFumeChance() {
		return fumeChance;
	}

	/**
	 * Determines if there are any registry entries that contains input as the stack to be smelted.
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
	 * @param input The stack to be tested
	 * @return The recipe that contains input as the stack to be smelted. Returns null if not recipe is found.
	 */
	@Nullable
	public static OvenSmeltingRecipe getRecipe(ItemStack input) {
		for(OvenSmeltingRecipe recipe : REGISTRY) {
			for (ItemStack stack : recipe.getInput().getMatchingStacks()) {
				if (ItemStack.areItemsEqual(stack, input)) {
					return recipe;
				}
			}
		}
		return null;
	}
}
