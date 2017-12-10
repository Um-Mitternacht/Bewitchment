package com.witchcraft.common.item.food;

import com.witchcraft.common.core.WitchcraftCreativeTabs;
import com.witchcraft.common.lib.LibItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 02/03/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ItemGinger extends ItemCrop {

	public ItemGinger() {
		super(LibItemName.GINGER, 4, 0.8F, false);
		setCreativeTab(WitchcraftCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.setFire(10);
	}
}
