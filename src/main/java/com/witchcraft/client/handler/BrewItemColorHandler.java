package com.witchcraft.client.handler;

import com.witchcraft.api.brew.BrewUtils;
import com.witchcraft.api.helper.NBTHelper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

/**
 * This class was created by Arekkuusu on 25/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class BrewItemColorHandler implements IItemColor {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		if (tintIndex == 1 && NBTHelper.hasTag(stack, BrewUtils.BREW_COLOR)) {
			return NBTHelper.getInteger(stack, BrewUtils.BREW_COLOR);
		}
		return 0xFFFFFF;
	}
}
