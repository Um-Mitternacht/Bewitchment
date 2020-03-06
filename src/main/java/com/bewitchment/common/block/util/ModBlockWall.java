package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;

/**
 * Created by Joseph on 3/6/2020.
 */

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ModBlockWall extends BlockWall {
	public ModBlockWall(String name, Block base, String... oreDictionaryNames) {
		super(base.getMaterial(this));
		Util.registerBlock(this, name, base, oreDictionaryNames);
	}
}
