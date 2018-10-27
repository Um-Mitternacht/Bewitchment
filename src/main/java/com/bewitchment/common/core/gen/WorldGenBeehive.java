package com.bewitchment.common.core.gen;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.statics.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenBeehive implements IWorldGenerator {

	private static void generateBeehives(World world, Random random, int chunkX, int chunkZ) {
		final BlockPos.MutableBlockPos variableBlockPos = new BlockPos.MutableBlockPos();

		if (random.nextFloat() < ModConfig.WORLD_GEN.beehive.beehive_gen_chance / 33.0f) {
			int x = chunkX + random.nextInt(16);
			int z = chunkZ + random.nextInt(16);
			int y = world.getHeight(x, z) - 1; // if there is a tree, world height will be just above the top leaves of the tree.
			variableBlockPos.setPos(x, y, z);

			if (!isBlockLeaves(world, variableBlockPos)) return;

			int newY = getHeightBelowLeaves(world, x, y, z);

			if (newY < 0) return;

			variableBlockPos.setY(newY);
			IBlockState state = ModBlocks.beehive.getDefaultState();
			int positionRand = random.nextInt(4);
			if (positionRand == 0) {
				state = state.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH);
			} else if (positionRand == 1) {
				state = state.withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH);
			} else if (positionRand == 2) {
				state = state.withProperty(BlockHorizontal.FACING, EnumFacing.EAST);
			} else if (positionRand == 3) {
				state = state.withProperty(BlockHorizontal.FACING, EnumFacing.WEST);
			}
			world.setBlockState(variableBlockPos, state);
		}
	}

	private static boolean isBlockLeaves(World world, BlockPos blockPos) {
		IBlockState blockState = world.getBlockState(blockPos);
		final Block block = blockState.getBlock();
		return block.isLeaves(blockState, world, blockPos);
	}

	private static int getHeightBelowLeaves(World world, int posX, int posY, int posZ) {
		final BlockPos.MutableBlockPos variableBlockPos = new BlockPos.MutableBlockPos();
		variableBlockPos.setPos(posX, posY, posZ);

		for (int y = posY; y >= world.getSeaLevel(); --y) {
			variableBlockPos.setY(y);
			final IBlockState blockState = world.getBlockState(variableBlockPos);
			final Block block = blockState.getBlock();

			if (block.isLeaves(blockState, world, variableBlockPos)) continue;

			if (world.isAirBlock(variableBlockPos)) return y;

			return -1;
		}
		return -1;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		final Biome biome = world.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));

		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
			return;
		}

		generateBeehives(world, random, chunkX * 16 + 8, chunkZ * 16 + 8);
	}

}