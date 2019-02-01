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
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.anyDye, LibIngredients.paper, LibIngredients.yewEssence)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.everchanging_presence.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("undying_image")
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.ectoplasm, LibIngredients.fumeReekOfDeath, LibIngredients.yewEssence)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.undying_image.ordinal()),
						new ItemStack(ModItems.ectoplasm, 2, 0)

				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("emanation_of_dishonesty")
				.withBaseProcessingTime(300)
				.withInput(LibIngredients.graveyardDust, LibIngredients.blazePowder, LibIngredients.oakAppleGall, LibIngredients.fumeBottledMagic)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.emanation_of_dishonesty.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("otherworldy_tears")
				.withBaseProcessingTime(600)
				.withInput(LibIngredients.enderPearl, LibIngredients.lapisPowder, LibIngredients.fumeBirchSoul)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.otherworld_tears.ordinal()),
						new ItemStack(ModItems.dimensional_sand, 2, 0)
				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("zephyr_of_the_depths")
				.withBaseProcessingTime(900)
				.withInput(LibIngredients.kelp, LibIngredients.coquina, LibIngredients.lapisPowder, LibIngredients.otherworldTears)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.zephyr_of_the_depths.ordinal()),
						new ItemStack(Items.SLIME_BALL, 2, 0)
				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("fiery_breeze")
				.withBaseProcessingTime(900)
				.withInput(LibIngredients.blazePowder, LibIngredients.woodAsh, LibIngredients.obsidian, LibIngredients.fumeCloudyOil)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.fiery_breeze.ordinal()),
						new ItemStack(ModItems.diabolic_vein, 2, 0)
				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("heavenly_winds")
				.withBaseProcessingTime(900)
				.withInput(LibIngredients.glowstoneDust, LibIngredients.quartzPowder, LibIngredients.jasper, LibIngredients.fumeBirchSoul)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.heavenly_winds.ordinal())
				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("petrichor_odour")
				.withBaseProcessingTime(900)
				.withInput(LibIngredients.obsidian, LibIngredients.coquina, LibIngredients.stone, LibIngredients.fumeBottledMagic)
				.withOutput(
						new ItemStack(ModItems.fume, 1, ItemFumes.Type.petrichor_odour.ordinal())
				)
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("demonic_elixir")
				.withBaseProcessingTime(300) //TODO: Add demon heart item
				.withInput(LibIngredients.graveyardDust, LibIngredients.blazePowder, LibIngredients.demonheart, LibIngredients.fumeCloudyOil)
				.withOutput(new ItemStack(ModItems.fume, 1, ItemFumes.Type.demonic_dew.ordinal()))
				.build()
		);
		REGISTRY.register(DistilleryRecipe.Factory.start("cleansing_aura")
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
