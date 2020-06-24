package com.bewitchment.common.world.gen.tree;

import com.bewitchment.common.world.gen.tree.util.WorldGenModTree;
import com.bewitchment.registry.ModObjects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("NullableProblems")
public class WorldGenCypressTree extends WorldGenModTree {
	public WorldGenCypressTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean canSaplingGrow(World world, BlockPos pos) {
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				for (int y = 0; y < 8; y++) {
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
		int h = generateTrunk(world, ModObjects.cypress_wood.getDefaultState(), pos, rand, 5, 13);
		for (int y = -h + 2; y < 2; y++) {
			boolean cross = y <= -1;
			boolean core = y > -1;
			boolean full = y >= -h + 3 && y <= -h / 2;
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos current = pos.up(h).add(x, y, z);
					if (world.getBlockState(current).getBlock().canBeReplacedByLeaves(world.getBlockState(current), world, current) && (core && z == 0 && x == 0 || full || cross && (z == 0 || x == 0)))
						world.setBlockState(current, ModObjects.cypress_leaves.getDefaultState());
				}
			}
		}
		return true;
	}
}