package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.item.Item;

public class ItemLeonardsWand extends Item {
	public ItemLeonardsWand() {
		super();
		Util.registerItem(this, "leonards_wand");
		setMaxDamage(128);
		setMaxStackSize(1);
	}
}
