package com.bewitchment.common.block.natural.crop;

import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * This class was created by Arekkuusu on 19/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class CropSilphium extends BlockCrop {

	public CropSilphium() {
		super(LibBlockName.CROP_SILPHIUM, 5);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		return canSustainBush(state);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		checkAndDropBlock(worldIn, pos, state);

		if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
			int age = this.getAge(state);

			if (age < 5) {
				float f = getGrowthChance(this, worldIn, pos);

				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0)) {
					worldIn.setBlockState(pos, this.withAge(age + 1), 2);
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
				}
			}
		}

		//State 6 means its a top block, so it cannot grow.
		//We give it a chance of happening, if it can DO IT :tm:, check if the biome is NOT dry (aka can rain).
		if (state.getValue(AGE) == 6 || rand.nextBoolean() || !worldIn.getBiome(pos).canRain()) return;

		//Now we check if we can grow on top of this block.
		if (canSustainBush(worldIn.getBlockState(pos.down())) && worldIn.isAirBlock(pos.up())) {
			IBlockState down = worldIn.getBlockState(pos.down());

			//If the block below is Silphium and the age is 3 we place a top block in this position so it stops growing.
			if (down.getBlock() == this && down.getValue(AGE) >= 3) {
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
					worldIn.setBlockState(pos, getDefaultState().withProperty(AGE, 6), 3);
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
				}
				//If it isn't then we place a new Silphium stem on top, only if the crop below is is full grown.
			} else if (state.getValue(AGE) == 5 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
				worldIn.setBlockState(pos.up(), getDefaultState(), 3);
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public void grow(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(AGE) == 6) return;
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
		int j = 5;

		if (i > j) {
			i = j;
		}

		worldIn.setBlockState(pos, this.withAge(i), 2);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(AGE) == 6 ? this.getCrop() : this.getSeed();
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canSustainBush(worldIn.getBlockState(pos.down()))) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.FARMLAND || state.getBlock() == this;
	}
}
