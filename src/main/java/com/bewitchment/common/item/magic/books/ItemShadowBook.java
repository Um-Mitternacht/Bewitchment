package com.bewitchment.common.item.magic.books;

import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;

/**
 * This class was created by Joseph on 4/22/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemShadowBook extends ItemMod {

	public ItemShadowBook() {
		super(LibItemName.SHADOW_BOOK);
		setMaxDamage(0);
		setCreativeTab(BewitchmentCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	//Todo: Functionality, allow it to store books from this mod.
}

