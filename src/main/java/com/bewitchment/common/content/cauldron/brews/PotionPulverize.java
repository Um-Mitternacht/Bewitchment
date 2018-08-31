package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Joseph on 5/2/2018.
 */
public class PotionPulverize extends BrewMod {

	public PotionPulverize() {
		super("pulverize", false, 0xFAEBD7, true, 0);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 2 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = world.rand.nextInt(7) <= amplifier;
			if (place) {
				if (state.getBlock() == Blocks.GRAVEL) {
					world.setBlockState(spot, Blocks.SAND.getDefaultState(), 3);
				} else if (state.getBlock() == Blocks.STONE) {
					world.setBlockState(spot, Blocks.GRAVEL.getDefaultState(), 3);
				} else if (state.getBlock() == Blocks.STONEBRICK) {
					world.setBlockState(spot, Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED), 3);
				}
			}
		}
	}
}
