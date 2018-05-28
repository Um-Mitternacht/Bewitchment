package com.bewitchment.client.handler;

import com.bewitchment.common.Bewitchment;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 11/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ColorPropertyHandler implements IBlockColor, IItemColor {

	public static final ColorPropertyHandler INSTANCE = new ColorPropertyHandler();

	private ColorPropertyHandler() {
	}

	private static final int getColor(int index) {
		switch (index) {
			case 0:
				return 16383998;
			case 1:
				return 16351261;
			case 2:
				return 13061821;
			case 3:
				return 3847130;
			case 4:
				return 16701501;
			case 5:
				return 8439583;
			case 6:
				return 15961002;
			case 7:
				return 4673362;
			case 8:
				return 10329495;
			case 9:
				return 1481884;
			case 10:
				return 8991416;
			case 11:
				return 3949738;
			case 12:
				return 8606770;
			case 13:
				return 6192150;
			case 14:
				return 11546150;
			case 15:
				return 1908001;
			default:
				return 0;
		}
	}

	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
		return getColor(state.getValue(Bewitchment.COLOR).getMetadata());
	}

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return getColor(stack.getMetadata());
	}
}
