package com.bewitchment.common.block.decorations;

/**
 * Created by Joseph on 9/3/2017.
 */
public class BlockFakeIceSlabDouble extends BlockFakeIceSlab {

	//FIXME: Make these less screwy.

	public BlockFakeIceSlabDouble(String unlocalizedName) {
		super(unlocalizedName);
	}

	@Override
	public boolean isDouble() {
		return true;
	}
}
