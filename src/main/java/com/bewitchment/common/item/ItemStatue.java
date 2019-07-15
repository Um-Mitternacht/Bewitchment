package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ItemStatue extends Item {
	private final Block toPlace;
	
	public ItemStatue(String name, Block toPlace) {
		super();
		Util.registerItem(this, name);
		this.toPlace = toPlace;
	}
}