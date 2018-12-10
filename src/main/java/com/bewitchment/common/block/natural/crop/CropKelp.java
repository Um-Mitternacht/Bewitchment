package com.bewitchment.common.block.natural.crop;

import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static net.minecraft.block.BlockLiquid.LEVEL;

/**
 * This class was created by Arekkuusu on 02/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class CropKelp extends BlockCrop {

	public CropKelp() {
		super(LibBlockName.CROP_KELP);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Material getMaterial(IBlockState state) {
		return Material.WATER;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (rand.nextInt(2) == 0 && isMaxAge(state) && canBlockStay(worldIn, pos, state)
				&& canPlaceBlockAt(worldIn, pos)
				&& canPlaceBlockAt(worldIn, pos.up())) {

			if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
				worldIn.setBlockState(pos.up(), getDefaultState(), 2);
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
		}
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).getBlock() == Blocks.WATER;
	}

	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		BlockPos I = pos.add(-1, 0, -1);
		BlockPos F = pos.add(1, 1, 1);
		for (BlockPos blockPos : BlockPos.getAllInBox(I, F)) {
			Block block = worldIn.getBlockState(blockPos).getBlock();
			if (block != this && block != Blocks.WATER && block != Blocks.FLOWING_WATER) {
				checkAndDropBlock(worldIn, pos, state);
				break;
			}
		}
		if (!canBlockStay(worldIn, pos, state) || !canSustainBush(worldIn.getBlockState(pos.down()))) {
			checkAndDropBlock(worldIn, pos, state);
		}
	}


	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 11);
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == this;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		state = worldIn.getBlockState(pos.down());
		return state.getMaterial().isSolid() || state.getBlock() == this;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LEVEL, AGE);
	}


}
