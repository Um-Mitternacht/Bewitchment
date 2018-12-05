package com.bewitchment.common.crafting;

import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import com.bewitchment.common.lib.LibIngredients;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModDistilleryRecipes {

	private static final ResourceLocation REGISTRY_LOCATION = new ResourceLocation(LibMod.MOD_ID, "distillery");
	public static final IForgeRegistry<DistilleryRecipe> REGISTRY = new RegistryBuilder<DistilleryRecipe>().disableSaving().setName(REGISTRY_LOCATION).setType(DistilleryRecipe.class).setIDRange(0, 1000).create();

	public static void init() {
		
		//All of these are temporary recipes! TODO
		
		REGISTRY.register(DistilleryRecipe.Factory.start("everchanging_presence")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.anyDye, LibIngredients.fumeBottledMagic)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.everchanging_presence.ordinal()))
				.build()
				);
		REGISTRY.register(DistilleryRecipe.Factory.start("undying_image")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.wax, LibIngredients.fumeBottledMagic)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.undying_image.ordinal()))
				.build()
				);
		REGISTRY.register(DistilleryRecipe.Factory.start("emanation_of_dishonesty")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.salt, LibIngredients.fumeBottledMagic)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.emanation_of_dishonesty.ordinal()))
				.build()
				);
		REGISTRY.register(DistilleryRecipe.Factory.start("cleansing_aura")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.anyGlass, LibIngredients.fumeBottledMagic)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.cleansing_aura.ordinal()),
						new ItemStack(Items.SLIME_BALL)
						)
				.build()
				);
	}
}
