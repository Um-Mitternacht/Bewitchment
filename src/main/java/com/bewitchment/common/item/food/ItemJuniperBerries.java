package com.bewitchment.common.item.food;

import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemJuniperBerries extends ItemModFood {

	public ItemJuniperBerries() {
		super(LibItemName.JUNIPER_BERRIES, 1, 0.5F, false);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		setPotionEffect(new PotionEffect(MobEffects.POISON, 450, 0), 0.1F);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		player.getFoodStats().addStats(1, 0.2f);
	}
}
