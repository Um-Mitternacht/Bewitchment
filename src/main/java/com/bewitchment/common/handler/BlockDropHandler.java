package com.bewitchment.common.handler;

import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class BlockDropHandler {
	@SubscribeEvent
	public void harvestDrops(BlockEvent.HarvestDropsEvent event) {
		replaceDrop(event, s -> s.getBlock() == ModObjects.salt_ore, new ItemStack(ModObjects.salt, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel()) + event.getWorld().rand.nextInt(4)), 100, 1, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.amethyst_ore, new ItemStack(ModObjects.amethyst, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel())), 100, 1, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.garnet_ore, new ItemStack(ModObjects.garnet, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel())), 100, 1, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.moonstone_ore, new ItemStack(ModObjects.moonstone, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel())), 100, 1, false);
		
		replaceDrop(event, s -> s.getBlock() == ModObjects.cypress_leaves, new ItemStack(ModObjects.cypress_sapling), 5, 1, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.elder_leaves, new ItemStack(ModObjects.elder_sapling), 5, 1, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.juniper_leaves, new ItemStack(ModObjects.juniper_sapling), 5, 1, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.yew_leaves, new ItemStack(ModObjects.yew_sapling), 5, 1, false);
		
		replaceDrop(event, s -> s.getBlock() == ModObjects.elder_leaves, new ItemStack(ModObjects.elderberries), 1, 0, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.juniper_leaves, new ItemStack(ModObjects.juniper_berries), 1, 0, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.yew_leaves, new ItemStack(ModObjects.yew_aril), 1, 0, false);
		replaceDrop(event, s -> s.getBlock() == Blocks.DEADBUSH, new ItemStack(ModObjects.white_sage_seeds), 15, 2, false);
		replaceDrop(event, s -> s.getBlock() == Blocks.RED_FLOWER && s.getValue(Blocks.RED_FLOWER.getTypeProperty()) == BlockFlower.EnumFlowerType.ALLIUM, new ItemStack(ModObjects.garlic_seeds), 15, 2, false);
		replaceDrop(event, s -> (s.getBlock() == Blocks.LEAVES && s.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK) || (s.getBlock() == Blocks.LEAVES2 && s.getValue(BlockNewLeaf.VARIANT) == BlockPlanks.EnumType.DARK_OAK), new ItemStack(ModObjects.oak_apple_gall), 2, 0, false);
	}
	
	private static int getFortuneDropAmount(Random rand, int fortuneLevel) {
		return fortuneLevel > 0 ? Math.max(1, rand.nextInt(fortuneLevel + 2)) : 1;
	}
	
	/**
	 * @param replaceFlag 0 means do not replace, 1 means always replace, 2 means replace if the random roll is successful
	 */
	private static void replaceDrop(BlockEvent.HarvestDropsEvent event, Predicate<IBlockState> predicate, ItemStack out, int chance, int replaceFlag, boolean ignoreSilkTouch) {
		if (predicate.test(event.getState()) && (ignoreSilkTouch || !event.isSilkTouching())) {
			if (replaceFlag == 1) event.getDrops().clear();
			if (event.getWorld().rand.nextInt(100) <= chance) {
				if (replaceFlag == 2) event.getDrops().clear();
				event.getDrops().add(out);
			}
		}
	}
}