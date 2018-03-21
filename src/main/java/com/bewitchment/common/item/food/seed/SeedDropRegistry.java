package com.bewitchment.common.item.food.seed;

import com.bewitchment.common.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

/**
 * This class was created by Joseph on 5/29/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Mod.EventBusSubscriber
public class SeedDropRegistry {

	public static void init() {

		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_belladonna), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_thistle), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_wormwood), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_asphodel), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_aconitum), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_mint), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_lavender), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_mandrake), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_tulsi), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_kenaf), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_hellebore), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_ginger), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_silphium), 1);
	}
}
