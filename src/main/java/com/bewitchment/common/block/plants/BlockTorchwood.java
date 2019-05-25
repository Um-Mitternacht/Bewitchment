package com.bewitchment.common.block.plants;

import com.bewitchment.Util;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Joseph on 5/25/2019.
 */
@SuppressWarnings("NullableProblems")
public class BlockTorchwood extends BlockBush {
	
	public BlockTorchwood() {
		super();
		Util.registerBlock(this, "torchwood", Material.PLANTS, SoundType.WOOD, 0.3f, 0.3f, "", 0);
		this.setLightLevel(0.7F);
	}
	
	@Override
	public boolean canSustainBush(IBlockState state) {
		return state.getMaterial() == Material.ROCK || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.SAND;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
	}
}
