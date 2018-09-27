package com.bewitchment.api.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class AltarModifierCollectionEvent extends BlockEvent {
	
	public int extraGain = 0;
	//This will be *added* to the multiplier, which has a base value of 1. So setting it to 0.1 will result in a 1.1 multiplier on the final value
	public double multiplier = 0;

	public AltarModifierCollectionEvent(World world, BlockPos pos, IBlockState state) {
		super(world, pos, state);
	}

}
