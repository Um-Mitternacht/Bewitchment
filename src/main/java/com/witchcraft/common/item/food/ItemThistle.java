package com.witchcraft.common.item.food;

import com.witchcraft.common.core.WitchcraftCreativeTabs;
import com.witchcraft.common.lib.LibItemName;
import net.minecraft.init.MobEffects;

/**
 * This class was created by Joseph on 02/03/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ItemThistle extends ItemCrop {

	public ItemThistle() {
		super(LibItemName.THISTLE, 4, 0.8F, false);
		addPotion(MobEffects.STRENGTH);
		setCreativeTab(WitchcraftCreativeTabs.PLANTS_CREATIVE_TAB);
	}
}
