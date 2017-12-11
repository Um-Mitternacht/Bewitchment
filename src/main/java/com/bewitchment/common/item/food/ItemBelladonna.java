package com.bewitchment.common.item.food;

import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 02/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemBelladonna extends ItemCrop {

	public ItemBelladonna() {
		super(LibItemName.BELLADONNA, 2, 1.5F, false);
		setCreativeTab(BewitchmentCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 10, 0));
	}
}
