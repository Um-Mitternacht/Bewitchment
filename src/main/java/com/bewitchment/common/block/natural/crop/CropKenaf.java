package com.bewitchment.common.block.natural.crop;

import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * This class was created by Arekkuusu on 17/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class CropKenaf extends BlockCrop {

	private static final AxisAlignedBB[] KENAF_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), null, null, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

	public CropKenaf() {
		super(LibBlockName.CROP_KENAF, 4);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return KENAF_AABB[getAge(state)];
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.FARMLAND || state.getBlock() == this;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (isMaxAge(state) && state.getValue(AGE) != 7 && canSustainBush(worldIn.getBlockState(pos.down())) && worldIn.isAirBlock(pos.up())) {
			int i = 1;
			while (worldIn.getBlockState(pos.down(i)).getBlock() == this) {
				++i;
			}

			if (i >= 5 || (i >= 3 && rand.nextInt(5) == 0)) {
				worldIn.setBlockState(pos, state.withProperty(AGE, 7), 2);
			} else if (rand.nextInt(5) == 0) {
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
					worldIn.setBlockState(pos.up(), getDefaultState());
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		return canSustainBush(state);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkForDrop(worldIn, pos, state);
	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (this.canSustainBush(worldIn.getBlockState(pos.down()))) {
			return true;
		}
		this.dropBlockAsItem(worldIn, pos, state, 0);
		worldIn.setBlockToAir(pos);
		return false;
	}
}
