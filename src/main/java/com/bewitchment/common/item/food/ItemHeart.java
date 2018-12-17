package com.bewitchment.common.item.food;

import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * This class was created by Joseph on 02/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemHeart extends ItemModFood {

	public ItemHeart() {
		super(LibItemName.HEART, 8, 1.6F, true);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 750, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 750, 3));
	}
}
