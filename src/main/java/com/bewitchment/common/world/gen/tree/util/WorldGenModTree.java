package com.bewitchment.common.world.gen.tree.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public abstract class WorldGenModTree extends WorldGenAbstractTree {
    protected WorldGenModTree(boolean notify) {
        super(notify);
    }

    protected int generateTrunk(World world, IBlockState state, BlockPos pos, Random rand, int minHeight, int maxHeight) {
        int height = minHeight + rand.nextInt(maxHeight - minHeight + 1);
        for (int i = 0; i < height; i++)
            if (world.getBlockState(pos.up(i)).getBlock().canBeReplacedByLeaves(world.getBlockState(pos.up(i)), world, pos.up(i)) || i == 0)
                world.setBlockState(pos.up(i), state);
        return height;
    }

    public abstract boolean canSaplingGrow(World world, BlockPos pos);
}