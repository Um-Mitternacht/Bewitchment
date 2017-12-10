package com.witchcraft.common.block.magic;

/**
 * Created by Joseph on 9/3/2017.
 */
public class BlockFakeIceSlabDouble extends BlockFakeIceSlab {

	public BlockFakeIceSlabDouble(String unlocalizedName) {
		super(unlocalizedName);
	}

	@Override
	public boolean isDouble() {
		return true;
	}
}
