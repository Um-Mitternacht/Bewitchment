package com.bewitchment.common.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * This class was created by Arekkuusu on 28/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public enum Crop {
	ACONITUM(Blocks.FARMLAND),
	ASPHODEL(Blocks.FARMLAND),
	BELLADONNA(Blocks.FARMLAND),
	GINGER(Blocks.FARMLAND),
	KELP(Blocks.WATER),
	MINT(Blocks.FARMLAND),
	WHITE_SAGE(Blocks.FARMLAND),
	MANDRAKE(Blocks.FARMLAND),
	LAVENDER(Blocks.FARMLAND),
	THISTLE(Blocks.FARMLAND),
	TULSI(Blocks.FARMLAND),
	KENAF(Blocks.FARMLAND),
	SILPHIUM(Blocks.FARMLAND),
	GARLIC(Blocks.FARMLAND),
	WORMWOOD(Blocks.FARMLAND),
	HELLEBORE(Blocks.FARMLAND);

	private final Block soil;

	Crop(Block soil) {
		this.soil = soil;
	}

	public Block getSoil() {
		return soil;
	}
}
