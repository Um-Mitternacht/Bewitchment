package com.bewitchment.common.handler;

import com.bewitchment.registry.ModObjects;
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
		replaceDrop(event, s -> s.getBlock() == ModObjects.salt_ore, new ItemStack(ModObjects.salt, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel()) + event.getWorld().rand.nextInt(4)), 100, true, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.amethyst_ore, new ItemStack(ModObjects.amethyst, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel())), 100, true, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.garnet_ore, new ItemStack(ModObjects.garnet, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel())), 100, true, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.moonstone_ore, new ItemStack(ModObjects.moonstone, getFortuneDropAmount(event.getWorld().rand, event.getFortuneLevel())), 100, true, false);

		replaceDrop(event, s -> s.getBlock() == ModObjects.cypress_leaves, new ItemStack(ModObjects.cypress_sapling), 5, true, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.elder_leaves, new ItemStack(ModObjects.elder_sapling), 5, true, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.juniper_leaves, new ItemStack(ModObjects.juniper_sapling), 5, true, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.yew_leaves, new ItemStack(ModObjects.yew_sapling), 5, true, false);

		replaceDrop(event, s -> s.getBlock() == ModObjects.elder_leaves, new ItemStack(ModObjects.elderberries), 1, false, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.juniper_leaves, new ItemStack(ModObjects.juniper_berries), 1, false, false);
		replaceDrop(event, s -> s.getBlock() == ModObjects.yew_leaves, new ItemStack(ModObjects.yew_aril), 1, false, false);
		//		replaceDrop(event, s -> s.getBlock() == Blocks.DEADBUSH, new ItemStack(ModObjects.seed_white_sage), 15, false, true, false);
		//		replaceDrop(event, s -> s.getBlock() == Blocks.RED_FLOWER && s.getValue(Blocks.RED_FLOWER.getTypeProperty()) == EnumFlowerType.ALLIUM, new ItemStack(ModObjects.seed_garlic), 15, false, true, false);
		replaceDrop(event, s -> (s.getBlock() == Blocks.LEAVES && s.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK) || (s.getBlock() == Blocks.LEAVES2 && s.getValue(BlockNewLeaf.VARIANT) == BlockPlanks.EnumType.DARK_OAK), new ItemStack(ModObjects.oak_apple_gall), 5, false, true);
	}

	private static int getFortuneDropAmount(Random rand, int fortuneLevel) {
		return fortuneLevel > 0 ? Math.max(0, rand.nextInt(fortuneLevel + 2)) : 1;
	}

	private static void replaceDrop(BlockEvent.HarvestDropsEvent event, Predicate<IBlockState> predicate, ItemStack out, int chance, boolean replace, boolean ignoreSilkTouch) {
		if (predicate.test(event.getState()) && (ignoreSilkTouch || !event.isSilkTouching())) {
			if (replace) event.getDrops().clear();
			if (event.getWorld().rand.nextInt(100) <= chance) event.getDrops().add(out);
		}
	}
}