package com.bewitchment.common.block;

import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by Joseph on 7/13/2019.
 */
public class BlockLeonardIdol extends ModBlock {
	public BlockLeonardIdol(String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level, String... oreDictionaryNames) {
		super(name, Material.ROCK, SoundType.STONE, 1, 3, "pickaxe", 0);
	}
}
