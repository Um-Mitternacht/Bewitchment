package com.bewitchment.common.core.gen;

import com.bewitchment.common.block.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenCoquina implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos position = world.getHeight(new BlockPos(chunkX * 16 + random.nextInt(16), 0, chunkZ * 16 + random.nextInt(16)));
		if (world.getBiome(position) == BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)) {
			while (world.getBlockState(position.down()).getBlock() == Blocks.SAND) {
				for (int i = 0; i < 3; i++) {
					int x = random.nextInt(2);
					int y = random.nextInt(2);
					int z = random.nextInt(2);
					for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-x, -y, -z), position.add(x, y, z))) {
						if (blockpos.distanceSq(position) <= Math.pow((x + y + z) * .333f + 0.5f, 2))
							world.setBlockState(blockpos, ModBlocks.coquina.getDefaultState());
					}
					position = position.add(random.nextInt(2) - 1, -random.nextInt(2), random.nextInt(2) - 1);
				}
				position = position.down();
			}
		}
		if (world.getBiome(position) == BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN)) {
			while (world.getBlockState(position.down()).getBlock() == Blocks.SAND) {
				for (int i = 0; i < 3; i++) {
					int x = random.nextInt(2);
					int y = random.nextInt(2);
					int z = random.nextInt(2);
					for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-x, -y, -z), position.add(x, y, z))) {
						if (blockpos.distanceSq(position) <= Math.pow((x + y + z) * .333f + 0.5f, 2))
							world.setBlockState(blockpos, ModBlocks.coquina.getDefaultState());
					}
					position = position.add(random.nextInt(2) - 1, -random.nextInt(2), random.nextInt(2) - 1);
				}
				position = position.down();
			}
		}
	}
}
