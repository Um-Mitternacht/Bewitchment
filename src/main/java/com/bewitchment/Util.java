package com.bewitchment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by Joseph on 4/28/2019.
 */
public class Util {

	public static final void giveAndConsumeItem(EntityPlayer player, EnumHand hand, ItemStack stack) {
		if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
		if (player.getHeldItem(hand).isEmpty()) player.setHeldItem(hand, stack);
		else if (!player.inventory.addItemStackToInventory(stack)) player.dropItem(stack, false);
	}
}
