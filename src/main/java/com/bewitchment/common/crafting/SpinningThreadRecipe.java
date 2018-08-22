package com.bewitchment.common.crafting;

import com.bewitchment.common.lib.LibMod;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SpinningThreadRecipe extends IForgeRegistryEntry.Impl<SpinningThreadRecipe> {

	private static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(LibMod.MOD_ID, "thread_spinning");
	public static final IForgeRegistry<SpinningThreadRecipe> REGISTRY = new RegistryBuilder<SpinningThreadRecipe>().disableSaving().setName(REGISTRY_LOCATION).setType(SpinningThreadRecipe.class).setIDRange(0, 200).create();

	private final ItemStack output;
	private final Ingredient[] inputs;

	/**
	 * @param regName The name of this entry in the forge registry with the format "mod:regName". Cannot be null.
	 * @param output  The output that will be produced by this recipe. Cannot be null.
	 * @param inputs  The inputs needed for this recipe. Cannot be null or have a size greater than four.
	 */
	public SpinningThreadRecipe(ResourceLocation regName, ItemStack output, Ingredient... inputs) {
		if (inputs.length > 4) {
			throw new IllegalArgumentException("The list of inputs cannot be greater than 4. ");
		}
		this.output = output;
		this.inputs = inputs;
		this.setRegistryName(regName);
	}

	/**
	 * Finds any registry entries that contains has this list as it's input.
	 *
	 * @param list The list of potential ingredients. Cannot be null.
	 * @return the recipe associated with the list of ingredients. Returns null if not recipe is found.
	 */
	@Nullable
	public static SpinningThreadRecipe getRecipe(NonNullList<ItemStack> list) {
		for (SpinningThreadRecipe recipe : REGISTRY) {
			if (recipe.matches(list)) {
				return recipe;
			}
		}
		return null;
	}

	/**
	 * @return the input ingredients.
	 */
	public Ingredient[] getInputs() {
		return inputs;
	}

	/**
	 * @return a copy of the output stack.
	 */
	public ItemStack getOutput() {
		return output.copy();
	}

	/**
	 * Determines if this recipe ingredients are contained in list.
	 *
	 * @param list The list of potential ingredients. Cannot be null.
	 * @return weather this is the list of ingredients for this recipe.
	 */
	public boolean matches(NonNullList<ItemStack> list) {
		int nonEmpty = 0;
		for (ItemStack is : list) {
			if (is.getCount() > 0) nonEmpty++;
		}
		if (nonEmpty != inputs.length) {
			return false;
		}
		boolean[] found = new boolean[inputs.length];
		ArrayList<ItemStack> comp = new ArrayList<>(list);
		for (int i = 0; i < inputs.length; i++) {
			Ingredient current = inputs[i];
			for (int j = 0; j < comp.size(); j++) {
				ItemStack is = comp.get(j);
				if (current.apply(is)) {
					found[i] = true;
					comp.set(j, ItemStack.EMPTY);
					break;
				}
			}
		}
		for (boolean b : found) {
			if (!b) {
				return false;
			}
		}
		return true;
	}

}
