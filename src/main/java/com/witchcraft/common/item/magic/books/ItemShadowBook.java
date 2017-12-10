package com.witchcraft.common.item.magic.books;

import com.witchcraft.common.core.WitchcraftCreativeTabs;
import com.witchcraft.common.item.ItemMod;
import com.witchcraft.common.lib.LibItemName;

/**
 * This class was created by Joseph on 4/22/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ItemShadowBook extends ItemMod {

	public ItemShadowBook() {
		super(LibItemName.SHADOW_BOOK);
		setMaxDamage(0);
		setCreativeTab(WitchcraftCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	//Todo: Functionality, allow it to store books from this mod.
}

