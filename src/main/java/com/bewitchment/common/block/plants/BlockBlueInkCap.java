package com.bewitchment.common.block.plants;

import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockBlueInkCap extends BlockBushSpreading {
	public BlockBlueInkCap() {
		super("blue_ink_cap");
		setSoundType(SoundType.SLIME);
	}

	@Override
	public boolean canSustainBush(IBlockState state) {
		return super.canSustainBush(state) || state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND;
	}
}
