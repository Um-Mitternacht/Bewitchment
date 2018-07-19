package com.bewitchment.api.crafting;

import com.bewitchment.api.helper.ItemStackHelper;
import com.bewitchment.common.lib.LibMod;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OvenSmeltingRecipe extends IForgeRegistryEntry.Impl<OvenSmeltingRecipe> {
	private static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(LibMod.MOD_ID, "oven");
	public static final IForgeRegistry<OvenSmeltingRecipe> REGISTRY = new RegistryBuilder<OvenSmeltingRecipe>().disableSaving().setName(REGISTRY_LOCATION).setType(OvenSmeltingRecipe.class).setIDRange(0, 200).create();

	private static final ArrayList<ItemStack> smeltables = new ArrayList<>();

	private ItemStack input, output, fumes;
	private int fumeChance;

	public OvenSmeltingRecipe(ItemStack input, ItemStack output, @Nullable ItemStack fumes, int fumeChance) {
		if(fumeChance > 100 || fumeChance < 0) { throw new IllegalArgumentException("fumeChance must be between 0 and 100. "); }
		this.input = input;
		smeltables.add(input);
		this.output = output;
		this.fumes = fumes;
		this.fumeChance = fumeChance;
	}

	public OvenSmeltingRecipe(String regName, ItemStack input, ItemStack output, @Nullable ItemStack fumes, int fumeChance) {
		this(input, output, fumes, fumeChance);
		this.setRegistryName(new ResourceLocation(LibMod.MOD_ID, regName));
	}

	public ItemStack getInput() {
		return input.copy();
	}

	public ItemStack getOutput() {
		return output.copy();
	}

	@Nullable
	public ItemStack getFumes() {
		return fumes;
	}

	public int getFumeChance() {
		return fumeChance;
	}

	public static boolean isSmeltable(ItemStack input) {
		return smeltables.stream().anyMatch(stack -> ItemStackHelper.haveSameItem(input, stack));
	}

	@Nullable
	public static OvenSmeltingRecipe getRecipe(ItemStack input) {
		for(OvenSmeltingRecipe recipe : REGISTRY) {
			if(recipe.getInput().getItem() == input.getItem() && recipe.getInput().getMetadata() == input.getMetadata()) {
				return recipe;
			}
		}
		return null;
	}
}
