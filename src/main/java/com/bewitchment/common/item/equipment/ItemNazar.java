package com.bewitchment.common.item.equipment;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.bewitchment.common.item.ItemMod;
import net.minecraft.item.ItemStack;

/**
 * Created by Joseph on 1/1/2018.
 */
public class ItemNazar extends ItemMod implements IBauble {
	public ItemNazar(String id) {
		super(id);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemStack) {
		return BaubleType.AMULET;
	}
}
