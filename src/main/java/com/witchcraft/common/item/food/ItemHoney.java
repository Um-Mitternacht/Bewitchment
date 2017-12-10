package com.witchcraft.common.item.food;

import com.witchcraft.common.core.WitchcraftCreativeTabs;
import com.witchcraft.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ItemHoney extends ItemModFood {

	public ItemHoney() {
		super(LibItemName.HONEY, 2, 4F, false);
		setCreativeTab(WitchcraftCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 450, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 450, 0));
	}
}
