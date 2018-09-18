package com.bewitchment.common.core;

import com.bewitchment.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * This class was created by Arekkuusu on 28/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public enum Crop {
	ACONITUM(Blocks.FARMLAND, 20),
	ASPHODEL(Blocks.FARMLAND),
	BELLADONNA(Blocks.FARMLAND, 20),
	GINGER(Blocks.FARMLAND),
	KELP(Blocks.WATER),
	MINT(Blocks.FARMLAND, 0),
	WHITE_SAGE(Blocks.FARMLAND),
	MANDRAKE(Blocks.FARMLAND, 20),
	LAVENDER(Blocks.FARMLAND, 0),
	THISTLE(Blocks.FARMLAND, 0),
	TULSI(Blocks.FARMLAND),
	KENAF(Blocks.FARMLAND),
	SILPHIUM(Blocks.FARMLAND),
	GARLIC(Blocks.FARMLAND, 0),
	WORMWOOD(Blocks.FARMLAND, 20),
	HELLEBORE(Blocks.FARMLAND, 20),
	SAGEBRUSH(Blocks.FARMLAND),
	INFESTED_WHEAT(ModBlocks.infested_farmland),
	CHRYSANTHEMUM(Blocks.FARMLAND);

	private final Block soil;
	private int mp;

	Crop(Block soil) {
		this.soil = soil;
		mp = 10;
	}
	
	Crop(Block soil, int magicValue) {
		this(soil);
		mp = magicValue;
	}

	public Block getSoil() {
		return soil;
	}
	
	public int getMP() {
		return mp;
	}
}
