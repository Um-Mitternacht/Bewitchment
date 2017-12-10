package com.witchcraft.common.item.food.seed;

import com.witchcraft.common.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * This class was created by Joseph on 5/29/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class SeedDropRegistry {

	public static void init() {

		//Todo: Add unique ways of getting some of the seeds (silphium from dungeons and strongholds, white sage from dead bushes)
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_mandrake), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_belladonna), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_aconitum), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_thistle), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_mint), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_ginger), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_wormwood), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_lavender), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_asphodel), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_tulsi), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_kenaf), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_garlic), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_silphium), 1);
	}
}
