package com.bewitchment.common.block.misc;

import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by Joseph on 10/25/2018.
 */
public class BlockGraveyardDirt extends BlockMod {
	public BlockGraveyardDirt() {
		super(LibBlockName.GRAVEYARD_DIRT, Material.GROUND);
		setResistance(1F);
		setHardness(1F);
		this.setSoundType(SoundType.GROUND);
	}

}
