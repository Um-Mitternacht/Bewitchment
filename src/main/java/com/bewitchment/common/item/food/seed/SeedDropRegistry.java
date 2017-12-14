package com.bewitchment.common.item.food.seed;

import com.bewitchment.common.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class was created by Joseph on 5/29/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Mod.EventBusSubscriber
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
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_silphium), 1);
	}
	
	@SubscribeEvent
	public static void onHarvestAllium(BlockEvent.HarvestDropsEvent harvest) {
		if (harvest.getState().getBlock() == Blocks.RED_FLOWER && harvest.getState().getBlock().getMetaFromState(harvest.getState()) == 2 && harvest.getWorld().rand.nextInt(5) == 0 && !harvest.isSilkTouching()) {
			harvest.getDrops().clear();
			harvest.getDrops().add(new ItemStack(ModItems.seed_garlic, 1));
		}
	}
}
