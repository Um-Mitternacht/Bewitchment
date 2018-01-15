package com.bewitchment.common.block.decorations;

import com.bewitchment.api.helper.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 9/3/2017.
 */
public class BlockFakeIceSlabHalf extends BlockFakeIceSlab implements IModelRegister {

	public BlockFakeIceSlabHalf(String unlocalizedName) {
		super(unlocalizedName);
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
