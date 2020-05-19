package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.ItemSword;

/**
 * Created by Joseph on 4/16/2020.
 */
public class ItemThyrsus extends ItemSword {
	
	public ItemThyrsus() {
		super(ModObjects.TOOL_THYRSUS);
		setMaxStackSize(1);
		Util.registerItem(this, "thyrsus");
		setMaxDamage(72);
	}
}
