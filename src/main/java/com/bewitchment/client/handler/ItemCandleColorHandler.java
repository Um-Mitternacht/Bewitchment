package com.bewitchment.client.handler;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

/**
 * This class was created by Arekkuusu on 11/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemCandleColorHandler implements IItemColor {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		int color = 0;
		switch (stack.getMetadata()) {
			case 0:
				color = 16383998;
				break;
			case 1:
				color = 16351261;
				break;
			case 2:
				color = 13061821;
				break;
			case 3:
				color = 3847130;
				break;
			case 4:
				color = 16701501;
				break;
			case 5:
				color = 8439583;
				break;
			case 6:
				color = 15961002;
				break;
			case 7:
				color = 4673362;
				break;
			case 8:
				color = 10329495;
				break;
			case 9:
				color = 1481884;
				break;
			case 10:
				color = 8991416;
				break;
			case 11:
				color = 3949738;
				break;
			case 12:
				color = 8606770;
				break;
			case 13:
				color = 6192150;
				break;
			case 14:
				color = 11546150;
				break;
			case 15:
				color = 1908001;
				break;
			default:
				break;
		}
		return color;
	}
}
