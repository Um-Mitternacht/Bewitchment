package com.bewitchment.common.item.tool;

import com.bewitchment.ModConfig;
import com.bewitchment.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSkeletonKey extends Item {
	public ItemSkeletonKey() {
		super();
		Util.registerItem(this, "skeleton_key");
		setMaxDamage(ModConfig.misc.maxSkeletonKeyUses);
		setMaxStackSize(1);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
}
