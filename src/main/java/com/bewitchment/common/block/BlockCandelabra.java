package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * Created by Joseph on 3/5/2020.
 */
public class BlockCandelabra extends ModBlock {
	public BlockCandelabra(String name) {
		super("candelabra_" + name, Material.IRON, SoundType.METAL, 1, 1, "pickaxe", 1);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
