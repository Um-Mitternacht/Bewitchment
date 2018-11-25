package com.bewitchment.common.crafting;

import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibIngredients;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModDistilleryRecipes {
	
	private static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(LibMod.MOD_ID, "distillery");
	public static final IForgeRegistry<DistilleryRecipe> REGISTRY = new RegistryBuilder<DistilleryRecipe>().disableSaving().setName(REGISTRY_LOCATION).setType(DistilleryRecipe.class).setIDRange(0, 1000).create();

	public static void init() {
		REGISTRY.register(DistilleryRecipe.Factory.start("test")
				.setAnyFluid().setAnyContainer()
				.withBaseProcessingTime(200)
				.withInput(LibIngredients.anyDye)
				.withOutput(new ItemStack(ModItems.catechu))
				.build());
	}
}
