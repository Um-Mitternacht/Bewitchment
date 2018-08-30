package com.bewitchment.common.item.food;

import com.bewitchment.common.core.ModCreativeTabs;
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
public class ItemFourThievesVinegar extends ItemModFood {

	public ItemFourThievesVinegar() {
		super(LibItemName.FOUR_THIEVES_VINEGAR, 2, 0.5F, false);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 450, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 450, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 150, 0));
	}
}
