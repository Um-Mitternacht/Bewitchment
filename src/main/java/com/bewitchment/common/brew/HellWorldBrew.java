package com.bewitchment.common.brew;

import com.bewitchment.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class HellWorldBrew extends BlockHitBrew {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	@SuppressWarnings("deprecation")
	public HellWorldBrew() {
		stateMap.put(Blocks.GRASS_PATH, Blocks.RED_NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.GRAVEL, Blocks.SOUL_SAND.getDefaultState());
		stateMap.put(Blocks.COBBLESTONE, Blocks.NETHERRACK.getDefaultState());
		stateMap.put(Blocks.PLANKS, Blocks.NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.OAK_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.SPRUCE_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.ACACIA_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.JUNGLE_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.BIRCH_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.DARK_OAK_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		stateMap.put(Blocks.END_BRICKS, Blocks.NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.BRICK_BLOCK, Blocks.NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.STONEBRICK, Blocks.NETHER_BRICK.getDefaultState());
		stateMap.put(Blocks.WOOL, Blocks.WOOL.getStateFromMeta(14));
	}

	@Override
	public int getColor() {
		return 0xED2939;
	}

	@Override
	public String getName() {
		return "hell_world";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) ((float) amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (BlockStairs.isBlockStairs(state)) {
				IBlockState newState = Blocks.NETHER_BRICK_STAIRS.getDefaultState()
						.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING))
						.withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF));
				world.setBlockState(spot, newState);
			} else if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			} else if (state.getBlock() == Blocks.LOG) {
				world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.LOG2) {
				world.setBlockState(spot, ModBlocks.nethersteel.getDefaultState(), 3);
			}
		}
	}
}
