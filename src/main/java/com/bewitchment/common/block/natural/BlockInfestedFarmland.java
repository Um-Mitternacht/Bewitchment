package com.bewitchment.common.block.natural;

import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockInfestedFarmland extends BlockMod implements IGrowable {

	public BlockInfestedFarmland() {
		super(LibBlockName.INFESTED_FARMLAND, Material.VINE);
		setResistance(1F);
		setHardness(1F);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
				worldIn.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
			} else {
				if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

						if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
							return;
						}

						IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

						if (iblockstate1.getBlock() == Blocks.FARMLAND && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
							worldIn.setBlockState(blockpos, ModBlocks.infested_farmland.getDefaultState());
						}
					}
				}
			}
		}
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		BlockPos blockpos = pos.up();

		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;
			int j = 0;

			while (true) {
				if (j >= i / 16) {
					if (worldIn.isAirBlock(blockpos1)) {
						if (rand.nextInt(8) == 0) {
							worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
						} else {
							IBlockState iblockstate1 = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

							if (Blocks.TALLGRASS.canBlockStay(worldIn, blockpos1, iblockstate1)) {
								worldIn.setBlockState(blockpos1, iblockstate1, 3);
							}
						}
					}

					break;
				}

				blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

				if (worldIn.getBlockState(blockpos1.down()).getBlock() != ModBlocks.infested_farmland || worldIn.getBlockState(blockpos1).isNormalCube()) {
					break;
				}

				++j;
			}
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;

	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}
}
