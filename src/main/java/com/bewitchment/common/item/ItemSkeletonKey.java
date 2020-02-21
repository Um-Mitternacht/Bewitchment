package com.bewitchment.common.item;

import com.bewitchment.ModConfig;
import com.bewitchment.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSkeletonKey extends Item {
	public ItemSkeletonKey() {
		super();
		Util.registerItem(this, "skeleton_key");
		setMaxDamage(ModConfig.misc.maxSkeletonKeyUses);
		setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("tooltip.bewitchment.skeleton_key"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
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
