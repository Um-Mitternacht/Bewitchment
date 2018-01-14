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
public class IceWorldBrew extends BlockHitBrew {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	@SuppressWarnings("deprecation")
	public IceWorldBrew() {
		stateMap.put(Blocks.GRASS_PATH, Blocks.PACKED_ICE.getDefaultState());
		stateMap.put(Blocks.GRAVEL, Blocks.PACKED_ICE.getDefaultState());
		stateMap.put(Blocks.COBBLESTONE, Blocks.PACKED_ICE.getDefaultState());
		stateMap.put(Blocks.LOG, Blocks.PACKED_ICE.getDefaultState());
		stateMap.put(Blocks.LOG2, Blocks.PACKED_ICE.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.SNOW.getDefaultState());
		stateMap.put(Blocks.GRASS, Blocks.SNOW.getDefaultState());
		stateMap.put(Blocks.MYCELIUM, Blocks.SNOW.getDefaultState());
		stateMap.put(Blocks.WOOL, Blocks.WOOL.getStateFromMeta(3));
	}

	@Override
	public int getColor() {
		return 0xB0E0E6;
	}

	@Override
	public String getName() {
		return "ice_world";
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
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			} else if (state.getBlock() == Blocks.LEAVES) {
				world.setBlockState(spot, ModBlocks.fake_ice.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.LEAVES2) {
				world.setBlockState(spot, ModBlocks.fake_ice.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.PLANKS) {
				world.setBlockState(spot, ModBlocks.fake_ice.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.STONE) {
				world.setBlockState(spot, ModBlocks.fake_ice.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.BRICK_BLOCK) {
				world.setBlockState(spot, ModBlocks.embittered_bricks.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.STONEBRICK) {
				world.setBlockState(spot, ModBlocks.embittered_bricks.getDefaultState(), 3);
			} else if (BlockStairs.isBlockStairs(state)) {
				IBlockState newState = ModBlocks.fake_ice_stairs.getDefaultState()
						.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING))
						.withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF));
				world.setBlockState(spot, newState);
			} else if (state.getBlock() == Blocks.OAK_FENCE) {
				world.setBlockState(spot, ModBlocks.fake_ice_fence.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.ACACIA_FENCE) {
				world.setBlockState(spot, ModBlocks.fake_ice_fence.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.BIRCH_FENCE) {
				world.setBlockState(spot, ModBlocks.fake_ice_fence.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.DARK_OAK_FENCE) {
				world.setBlockState(spot, ModBlocks.fake_ice_fence.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.JUNGLE_FENCE) {
				world.setBlockState(spot, ModBlocks.fake_ice_fence.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.SPRUCE_FENCE) {
				world.setBlockState(spot, ModBlocks.fake_ice_fence.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.SANDSTONE) {
				world.setBlockState(spot, ModBlocks.fake_ice.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.NETHER_BRICK) {
				world.setBlockState(spot, ModBlocks.embittered_bricks.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.RED_NETHER_BRICK) {
				world.setBlockState(spot, ModBlocks.embittered_bricks.getDefaultState(), 3);
			} else if (state.getBlock() == Blocks.END_BRICKS) {
				world.setBlockState(spot, ModBlocks.embittered_bricks.getDefaultState(), 3);
			} else if (state.getBlock() == ModBlocks.scorned_bricks) {
				world.setBlockState(spot, ModBlocks.embittered_bricks.getDefaultState(), 3);
			}
		}
	}
}
