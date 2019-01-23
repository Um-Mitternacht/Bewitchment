package com.bewitchment.common.item.tool;

import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;

/**
 * Created by Joseph on 1/23/2019.
 */
public class ItemStoneMortarAndPestle extends ItemMod {
	public ItemStoneMortarAndPestle() {
		super(LibItemName.MORTAR_AND_PESTLE_STONE);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		setContainerItem(this);
	}
}
