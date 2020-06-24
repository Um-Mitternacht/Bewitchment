package com.bewitchment.common.world.gen.tree;

import com.bewitchment.common.world.gen.tree.util.WorldGenModTree;
import com.bewitchment.registry.ModObjects;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("NullableProblems")
public class WorldGenJuniperTree extends WorldGenModTree {
	public WorldGenJuniperTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean canSaplingGrow(World world, BlockPos pos) {
		for (int x = -2; x < 3; x++) {
			for (int z = -2; z < 3; z++) {
				for (int y = 0; y < 2; y++) {
					BlockPos current = pos.up(2).add(x, y, z);
					if (!world.getBlockState(current).getBlock().canBeReplacedByLeaves(world.getBlockState(current), world, current))
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int h = generateTrunk(world, ModObjects.juniper_wood.getDefaultState(), pos, rand, 2, 4);
		EnumFacing branchOffset = EnumFacing.HORIZONTALS[rand.nextInt(4)];
		BlockPos branching = pos.up(h).offset(branchOffset);
		ArrayList<BlockPos> logs = new ArrayList<>();
		if (world.getBlockState(branching).getBlock().canBeReplacedByLeaves(world.getBlockState(branching), world, branching)) {
			world.setBlockState(branching, ModObjects.juniper_wood.getDefaultState());
			logs.add(branching);
		}
		BlockPos other = branching.offset(branchOffset.getOpposite(), 2);
		if (world.getBlockState(other).getBlock().canBeReplacedByLeaves(world.getBlockState(other), world, other)) {
			world.setBlockState(other, ModObjects.juniper_wood.getDefaultState());
			logs.add(other);
		}
		for (int i = 0; i < h / 2; i++) {
			BlockPos current = branching.up().offset(branchOffset, i + 1);
			if (world.getBlockState(current).getBlock().canBeReplacedByLeaves(world.getBlockState(current), world, current)) {
				logs.add(current);
				world.setBlockState(current, ModObjects.juniper_wood.getDefaultState());
			}
		}
		for (BlockPos pos0 : logs) {
			for (EnumFacing face : EnumFacing.VALUES) {
				BlockPos pos1 = pos0.offset(face);
				if (world.getBlockState(pos1).getBlock().canBeReplacedByLeaves(world.getBlockState(pos1), world, pos1))
					world.setBlockState(pos1, ModObjects.juniper_leaves.getDefaultState());
				for (EnumFacing face0 : EnumFacing.VALUES) {
					if (face0 != EnumFacing.DOWN) {
						BlockPos pos2 = pos0.offset(face).offset(face0);
						if (world.getBlockState(pos2).getBlock().canBeReplacedByLeaves(world.getBlockState(pos2), world, pos2) && rand.nextDouble() < 0.8)
							world.setBlockState(pos2, ModObjects.juniper_leaves.getDefaultState());
					}
				}
			}
		}
		return true;
	}
}