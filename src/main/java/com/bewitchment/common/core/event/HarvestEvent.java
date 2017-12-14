package com.bewitchment.common.core.event;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 10/10/2017.
 */
public class HarvestEvent {

	@SubscribeEvent
	public void onHarvestAllium(BlockEvent.HarvestDropsEvent harvest) {
		if (harvest.getState().getBlock() == Blocks.RED_FLOWER && harvest.getState().getBlock().getMetaFromState(harvest.getState()) == 2 && harvest.getWorld().rand.nextInt(5) == 0 && !harvest.isSilkTouching()) {
			harvest.getDrops().clear();
			harvest.getDrops().add(new ItemStack(ModItems.seed_garlic, 1));
		}
	}

	@SubscribeEvent
	public void onHarvestDeadBush(BlockEvent.HarvestDropsEvent harvest) {
		if ((harvest.getState().getBlock() == Blocks.DEADBUSH && harvest.getWorld().rand.nextInt(150) < 25)) {
			harvest.getDrops().clear();
			harvest.getDrops().add(new ItemStack(ModItems.seed_white_sage, 1));
		}
	}

	@SubscribeEvent
	public void onHarvestOakLeaves(BlockEvent.HarvestDropsEvent event) {
		if ((event.getState().getBlock() == Blocks.LEAVES && event.getWorld().rand.nextInt(150) < 25)) {
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(ModItems.oak_apple_gall, 1));
		}
	}

	@SubscribeEvent
	public void onScoopGravel(BlockEvent.HarvestDropsEvent event) {
		if ((event.getState().getBlock() == Blocks.GRAVEL && event.getWorld().rand.nextInt(400) < 25)) {
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(ModItems.gem, 1, 1));
		}
	}

	@SubscribeEvent
	public void onMineCoquina(BlockEvent.HarvestDropsEvent event) {
		if ((event.getState().getBlock() == ModBlocks.coquina && event.getWorld().rand.nextInt(345) < 25)) {
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(Blocks.GRAVEL, 1));
		}
	}

	@SubscribeEvent
	public void onHarvestWormwood(BlockEvent.HarvestDropsEvent event) {
		if ((event.getState().getBlock() == ModBlocks.crop_wormwood && event.getWorld().rand.nextInt(450) < 25)) {
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(ModItems.absinthe_green, 1));
			event.getDrops().add(new ItemStack(ModItems.wormwood, 3));
			event.getDrops().add(new ItemStack(ModItems.seed_wormwood, 2));
		}
	}
}