package com.bewitchment.common.core.event;

import com.bewitchment.common.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 10/10/2017.
 */
@Mod.EventBusSubscriber
public class HarvestEvents {

	@SubscribeEvent
	public static void dropController(HarvestDropsEvent event) {
		if (event.getState().getBlock() == Blocks.RED_FLOWER && event.getState().getValue(Blocks.RED_FLOWER.getTypeProperty()) == BlockFlower.EnumFlowerType.ALLIUM && event.getWorld().rand.nextInt(5) == 0) {
			//Allium -> garlic seeds
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(ModItems.seed_garlic, 1));
		} else if ((event.getState().getBlock() == Blocks.DEADBUSH && event.getWorld().rand.nextInt(6) == 0)) {
			//Dead bush -> sage/sagebrush
			event.getDrops().clear();
			if (event.getWorld().rand.nextBoolean()) {
				event.getDrops().add(new ItemStack(ModItems.seed_sagebrush, 1));
			} else {
				event.getDrops().add(new ItemStack(ModItems.seed_white_sage, 1));
			}
		} else if (((event.getState().getBlock() == Blocks.LEAVES && event.getState().getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK) || (event.getState().getBlock() == Blocks.LEAVES2 && event.getState().getValue(BlockNewLeaf.VARIANT) == BlockPlanks.EnumType.DARK_OAK)) && event.getWorld().rand.nextInt(6) == 0 && event.getState().getValue(BlockLeaves.DECAYABLE)) {
			//Oak and dark oak -> apple gall
			event.getDrops().clear();
			event.getDrops().add(new ItemStack(ModItems.oak_apple_gall, 1));
		}
	}
}