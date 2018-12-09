package com.bewitchment.common.crafting;

import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
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

		//All of these are temporary recipes! TODO

		REGISTRY.register(DistilleryRecipe.Factory.start("everchanging_presence")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.anyDye, LibIngredients.paper, LibIngredients.yewEssence)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.everchanging_presence.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("undying_image")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.wax, LibIngredients.fumeReekOfDeath)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.undying_image.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("emanation_of_dishonesty")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.graveyardDust, LibIngredients.blazePowder, LibIngredients.oakAppleGall, LibIngredients.fumeBottledMagic)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.emanation_of_dishonesty.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("otherworldy_tears")
				.setAnyContainer()//TODO: Make this take some water
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.enderPearl, LibIngredients.lapisPowder, LibIngredients.fumeBirchSoul)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.otherworld_tears.ordinal()),
						new ItemStack(ModItems.dimensional_sand, 2, 0)
				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("demonic_elixir")
				.setAnyContainer()
				.withBaseProcessingTime(300) //TODO: Add demon heart item
				.withInput(LibIngredients.graveyardDust, LibIngredients.blazePowder, LibIngredients.eyes, LibIngredients.fumeCloudyOil)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.demonic_dew.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("cleansing_aura")
				.setAnyContainer()
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.whiteSage, LibIngredients.sagebrush, LibIngredients.tulsi, LibIngredients.acaciaResin)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.cleansing_aura.ordinal()),
						new ItemStack(ModItems.wood_ash)
				)
				.build()
		);
	}
}
