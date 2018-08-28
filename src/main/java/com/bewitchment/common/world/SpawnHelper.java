package com.bewitchment.common.world;

import com.bewitchment.common.entity.living.familiar.EntityOwl;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Joseph on 8/28/2018.
 */
public class SpawnHelper implements IWorldGenerator {

	public static BlockPos getHeight(World world, BlockPos pos) {
		return world.getHeight(pos);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		int x = (chunkX * 16) + 8;
		int z = (chunkZ * 16) + 8;
		BlockPos height = getHeight(world, new BlockPos(x, 0, z));
		if (BiomeDictionary.hasType(world.getBiome(height), BiomeDictionary.Type.FOREST) && random.nextInt(3) == 0) {
			for (int i = 0; i < random.nextInt(5); i++) {
				BlockPos pos = new BlockPos(x + random.nextInt(10) - 5, 20 + random.nextInt(40), z + random.nextInt(10) - 5);
				if (world.getBlockState(pos).getMaterial() == Material.GRASS) {
					EntityOwl owl = new EntityOwl(world);
					owl.setLocationAndAngles(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 0, 0);
					if (owl.isNotColliding()) {
						world.spawnEntity(owl);
					}
				}
			}
		}
	}
}
