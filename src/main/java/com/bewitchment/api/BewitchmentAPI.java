package com.bewitchment.api;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.crop.Crop;
import com.bewitchment.api.crop.ICrop;
import com.bewitchment.api.recipe.IBrewModifier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class BewitchmentAPI {
	
	private static BewitchmentAPI INSTANCE;
	
	public static void setupAPI(BewitchmentAPI api) {
		if (INSTANCE == null) {
			INSTANCE = api;
		} else {
			throw new IllegalStateException("Bewitchment API already initialized");
		}
	}
	
	public static BewitchmentAPI getAPI() {
		if (INSTANCE != null) {
			return INSTANCE;
		}
		throw new IllegalStateException("Bewitchment API not ready yet");
	}
	
	public abstract void registerBrew(IBrew brew, ResourceLocation name);
	
	public abstract void registerBrewRecipe(ItemStack stack, Object... objects);
	
	public abstract <T> void registerItemEffect(ItemStack stack, T effect, boolean strict);
	
	public abstract void registerItemModifier(ItemStack stack, IBrewModifier modifier, boolean strict);
	
	public abstract void registerFoodValue(ItemStack stack, int hunger, float saturation);
	
	public abstract void registerItemRitual(ItemStack stack, int cost, Object... objects);
	
	public abstract void registerFluidIngredient(Item item, FluidStack fluid);
	
	/**
	 * Register an Item to the Processing factory.
	 *
	 * @param fluid  The fluid this Item needs
	 * @param in     The Item you throw in
	 * @param out    The Item that comes out
	 * @param strict If the Item must be identical
	 */
	public abstract void registerItemProcessing(Fluid fluid, ItemStack in, ItemStack out, boolean strict);
	
	/**
	 * Register a Crop to the crop registry.
	 * <p>
	 * The Item Seed needs to be different, for ex the Kelp seed,
	 * that needs to be placed on water so it uses a different placement recipeDropLogic.
	 * </p>
	 *
	 * @param crop The Crop enum
	 * @param cropItem The item this Crop will drop when harvested
	 * @param seedItem The seed that will place the Crop
	 */
	public abstract <T extends Block & ICrop> void registerCrop(Crop crop, T placed, Item cropItem, Item seedItem);
	
}
