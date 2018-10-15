package com.bewitchment.common.content.enchantments;

import net.minecraft.item.ItemStack;

public class EnchantmentPlaceholder extends BaublesEnchantment {

	protected EnchantmentPlaceholder(String name, Rarity rarityIn, int maxLevelIn) {
		super(name, rarityIn, maxLevelIn);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return false;
	}
}
