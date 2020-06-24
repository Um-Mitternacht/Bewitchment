package com.bewitchment.common.block.util;

import com.bewitchment.client.misc.Statues;
import com.bewitchment.common.block.BlockStatue;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Joseph on 6/19/2020.
 */
public class BlockIdol extends BlockStatue {
	public BlockIdol(Statues.Statue statue) {
		super(statue);
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos) {
		return 1.5f;
	}
}
