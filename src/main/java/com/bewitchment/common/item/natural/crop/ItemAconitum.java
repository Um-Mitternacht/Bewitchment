package com.bewitchment.common.item.natural.crop;

import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibItemName;
import com.bewitchment.common.potion.ModPotions;
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
public class ItemAconitum extends ItemCropFood {

	public ItemAconitum() {
		super(LibItemName.ACONITUM, 2, 0.6F, false);
		this.setAlwaysEdible();
		setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		player.addPotionEffect(new PotionEffect(MobEffects.POISON, 20, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20, 0));
		player.addPotionEffect(new PotionEffect(ModPotions.wolfsbane, 20, 0));
	}
}
