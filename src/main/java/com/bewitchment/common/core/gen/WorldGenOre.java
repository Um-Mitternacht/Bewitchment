package com.bewitchment.common.core.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * This class was created by BerciTheBeast on 4.3.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class WorldGenOre implements IWorldGenerator {
	private final IBlockState toGen;
	private final int minSize, maxSize, minHeight, maxHeight, chance;

	public WorldGenOre(IBlockState toGen, int minSize, int maxSize, int minHeight, int maxHeight, int chance) {
		this.toGen = toGen;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.chance = chance;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			WorldGenerator gen = new WorldGenMinable(toGen, random.nextInt(maxSize - minSize) + minSize);
			for (int i = 0; i < chance; i++) {
				gen.generate(world, random, new BlockPos(chunkX * 16 + random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, chunkZ * 16 + random.nextInt(16)));
			}
		}
	}
}
