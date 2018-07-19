package com.bewitchment.api.helper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class ItemStackHelper {
	public static boolean haveSameItem(ItemStack itemStack1, ItemStack itemStack2) {
		return itemStack1.getItem() == itemStack2.getItem() && itemStack1.getMetadata() == itemStack2.getMetadata();
	}

	public static boolean haveSameItem(ItemStack itemStack1, Item item2, int meta2) {
		return itemStack1.getItem() == item2 && itemStack1.getMetadata() == meta2;
	}

	public static void dropItems(IItemHandler handler, World world, BlockPos pos) {
		for (int i = 0; i < handler.getSlots(); ++i) {
			final ItemStack stack = handler.getStackInSlot(i);
			if (!stack.isEmpty()) {
				final EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
				world.spawnEntity(item);
			}
		}
	}
}
