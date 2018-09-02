package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionTillLand extends BrewMod {

	public PotionTillLand() {
		super("amplifier", false, 0x602312, true, 0);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);
		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = world.rand.nextInt(6) <= amplifier;
			if (place && state.getBlock() == Blocks.GRASS && world.isAirBlock(spot.up())) {
				world.setBlockState(spot, Blocks.FARMLAND.getDefaultState(), 3);
			}
		}
	}
}
