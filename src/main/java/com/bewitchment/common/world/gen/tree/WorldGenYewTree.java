package com.bewitchment.common.world.gen.tree;

import com.bewitchment.common.world.gen.tree.util.WorldGenModTree;
import com.bewitchment.registry.ModObjects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenYewTree extends WorldGenModTree {
	public WorldGenYewTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean canSaplingGrow(World world, BlockPos pos) {
		boolean flag = false;
		for (int x = 0; x >= -1; x--) {
			for (int z = 0; z >= -1; z--) {
				if (world.getBlockState(pos.add(x, 0, z)).getBlock() == ModObjects.yew_sapling && world.getBlockState(pos.add(x + 1, 0, z)).getBlock() == ModObjects.yew_sapling && world.getBlockState(pos.add(x, 0, z + 1)).getBlock() == ModObjects.yew_sapling && world.getBlockState(pos.add(x + 1, 0, z + 1)).getBlock() == ModObjects.yew_sapling)
					flag = true;
			}
		}
		if (!flag) return false;
		for (int x = -2; x < 3; x++) {
			for (int z = -2; z < 3; z++) {
				for (int y = 0; y < 3; y++) {
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
		int h1 = generateTrunk(world, ModObjects.yew_wood.getDefaultState(), pos, rand, 4, 6);
		int h2 = generateTrunk(world, ModObjects.yew_wood.getDefaultState(), world.getBlockState(pos.east()).getBlock() == ModObjects.yew_sapling ? pos.east() : pos.west(), rand, 4, 6);
		int h3 = generateTrunk(world, ModObjects.yew_wood.getDefaultState(), world.getBlockState(pos.east().north()).getBlock() == ModObjects.yew_sapling ? pos.east().north() : world.getBlockState(pos.east().south()).getBlock() == ModObjects.yew_sapling ? pos.east().south() : world.getBlockState(pos.west().north()).getBlock() == ModObjects.yew_sapling ? pos.west().north() : pos.west().south(), rand, 4, 6);
		int h4 = generateTrunk(world, ModObjects.yew_wood.getDefaultState(), world.getBlockState(pos.north()).getBlock() == ModObjects.yew_sapling ? pos.north() : pos.south(), rand, 4, 6);
		int hMin = Math.min(Math.min(h1, h2), Math.min(h3, h4));
		int hMax = Math.max(Math.max(h1, h2), Math.max(h3, h4));
		for (int x = -2; x < 4; x++) {
			for (int z = -3; z < 3; z++) {
				for (int y = -2; y < hMax - hMin + 2; y++) {
					BlockPos current = pos.up(hMin).add(x, y, z);
					if (world.getBlockState(current).getBlock().canBeReplacedByLeaves(world.getBlockState(current), world, current) && !((x == -2 || x == 3 || z == -3 || z == 2) && (rand.nextDouble() < 0.1 || y >= hMax - hMin) && (x == -1 || x == 2 || z == -2 || z == 1) && y == hMax - hMin + 1 || x == -2 && z == -3 || x == -2 && z == 2 || x == 3 && z == -3 || x == 3 && z == 2))
						world.setBlockState(current, ModObjects.yew_leaves.getDefaultState());
				}
			}
		}
		return true;
	}
}