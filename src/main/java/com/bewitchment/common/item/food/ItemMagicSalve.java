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
public class ItemMagicSalve extends ItemModFood {

	public ItemMagicSalve() {
		super(LibItemName.MAGIC_SALVE, 0, 0.5F, false);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.POISON, 1000, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 1000, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1000, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1000, 1));
	}
}
