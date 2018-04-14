package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.api.cauldron.modifiers.BewitchmentModifiers;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.potion.BrewMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Joseph on 4/14/2018.
 */
public class PotionFireWorld extends BrewMod {

	public PotionFireWorld() {
		super("fireworld", true, 0xED2939, true, 0);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(BewitchmentModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			if (spot.distanceSq(pos) < 2 + box * box / 2) {
				IBlockState state = world.getBlockState(spot);
				if (world.rand.nextInt(4) <= modifiers.getLevel(BewitchmentModifiers.POWER).orElse(0) / 2) {
					if (BlockStairs.isBlockStairs(state)) {
						IBlockState newState = Blocks.NETHER_BRICK_STAIRS.getDefaultState()
								.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING))
								.withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF));
						world.setBlockState(spot, newState);
					} else if (state.getBlock() == Blocks.LOG) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.LOG2) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.BRICK_BLOCK) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.STONEBRICK) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.END_BRICKS) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.embittered_bricks) {
						world.setBlockState(spot, ModBlocks.scorned_bricks.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.fake_ice_fence) {
						world.setBlockState(spot, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_cypress) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_elder) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_yew) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					} else if (state.getBlock() == ModBlocks.log_juniper) {
						world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
					}
				}
			}
		}
	}
}