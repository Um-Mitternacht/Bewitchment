package com.bewitchment.common.core.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class BlockStreamHelper {

	public static Stream<BlockPos> ofPos(BlockPos start, BlockPos end) {
		Stream.Builder<BlockPos> builder = Stream.builder();
		BlockPos.getAllInBox(start, end).forEach(bp -> builder.accept(bp));
		return builder.build();
	}

	public static Stream<IBlockState> ofStates(World world, BlockPos start, BlockPos end) {
		Stream.Builder<IBlockState> builder = Stream.builder();
		BlockPos.getAllInBox(start, end).forEach(bp -> builder.accept(world.getBlockState(bp)));
		return builder.build();
	}

	public static Stream<Tuple<IBlockState, BlockPos>> of(World world, BlockPos start, BlockPos end) {
		Stream.Builder<Tuple<IBlockState, BlockPos>> builder = Stream.builder();
		BlockPos.getAllInBox(start, end).forEach(bp -> builder.accept(new Tuple<>(world.getBlockState(bp), bp)));
		return builder.build();
	}

}
