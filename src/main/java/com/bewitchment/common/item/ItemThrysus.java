package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

/**
 * Created by Joseph on 4/16/2020.
 */
public class ItemThrysus extends ItemSword
{
	
	public ItemThrysus() {
		super(ModObjects.TOOL_THYRSUS);
		setMaxStackSize(1);
		Util.registerItem(this, "thrysus");
		setMaxDamage(72);
	}
}
