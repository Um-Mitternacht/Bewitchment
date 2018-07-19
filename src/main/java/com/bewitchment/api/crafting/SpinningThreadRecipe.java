package com.bewitchment.api.crafting;

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

	public static final IForgeRegistry<SpinningThreadRecipe> REGISTRY = new RegistryBuilder<SpinningThreadRecipe>().disableSaving().setName(new ResourceLocation(LibMod.MOD_ID, "thread_spinning")).setType(SpinningThreadRecipe.class).setIDRange(0, 200).create();

	private final ItemStack output;
	private final Ingredient[] inputs;

	public SpinningThreadRecipe(ItemStack output, Ingredient... inputs) {
		this.output = output;
		this.inputs = inputs;
	}

	public SpinningThreadRecipe(String regName, ItemStack output, Ingredient... inputs) {
		this(output, inputs);
		this.setRegistryName(LibMod.MOD_ID, regName);
	}

	@Nullable
	public static SpinningThreadRecipe getRecipe(NonNullList<ItemStack> list) {
		for (SpinningThreadRecipe recipe : REGISTRY) {
			if (recipe.matches(list)) {
				return recipe;
			}
		}
		return null;
	}

	public ItemStack getResult() {
		return output.copy();
	}

	public Ingredient[] getInputs() {
		return inputs;
	}

	public boolean matches(NonNullList<ItemStack> list) {
		int nonEmpty = 0;
		for (ItemStack is : list) {
			if (is.getCount() > 0) nonEmpty++;
		}
		if (nonEmpty != inputs.length) {
			return false;
		}
		boolean[] found = new boolean[inputs.length];
		ArrayList<ItemStack> comp = new ArrayList<ItemStack>(list);
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
