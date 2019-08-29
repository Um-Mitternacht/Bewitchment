package com.bewitchment.common.block.plants;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings({"NullableProblems","WeakerAccess"})
public class BlockSpanishMoss extends BlockVine {
    public static final PropertyBool TERMINAL = PropertyBool.create("terminal");

    public BlockSpanishMoss() {
        super();
        Util.registerBlock(this, "spanish_moss", Blocks.VINE);
        setTickRandomly(true);
        setDefaultState(getBlockState().getBaseState().withProperty(TERMINAL, false).withProperty(UP, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(EAST, false).withProperty(WEST, false));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote && !state.getValue(TERMINAL) && worldIn.isAirBlock(pos.down())) {
            IBlockState newState = ModObjects.spanish_moss.getDefaultState().withProperty(TERMINAL, rand.nextInt(4) == 0);
            newState = newState.withProperty(UP, state.getValue(UP));
            newState = newState.withProperty(NORTH, state.getValue(NORTH));
            newState = newState.withProperty(SOUTH, state.getValue(SOUTH));
            newState = newState.withProperty(EAST, state.getValue(EAST));
            newState = newState.withProperty(WEST, state.getValue(WEST));
            worldIn.setBlockState(pos.down(), newState, 3);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SOUTH, (meta & 3) == 0).withProperty(WEST, (meta & 3) == 1).withProperty(NORTH, (meta & 3) == 2).withProperty(EAST, (meta & 3) == 3).withProperty(UP, (meta & 4) > 0).withProperty(TERMINAL, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if(state.getValue(WEST)) i = 1;
        if(state.getValue(NORTH)) i = 2;
        if(state.getValue(EAST)) i = 3;
        if(state.getValue(UP)) i |= 4;
        if(state.getValue(TERMINAL)) i |= 8;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, SOUTH, EAST, WEST, UP, TERMINAL);
    }
}
