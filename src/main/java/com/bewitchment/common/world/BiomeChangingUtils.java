package com.bewitchment.common.world;

import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.google.common.collect.HashMultimap;
import com.miskatonicmysteries.common.world.gen.BiomeManipulator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Iterator;
import java.util.Set;

/**
 * Original code created by Ael.
 */
public class BiomeChangingUtils {

	public static void setMultiBiome(World world, Biome biome, BlockPos... poses) {
		byte id = (byte) Biome.getIdForBiome(biome);
		HashMultimap<ChunkPos, BlockPos> changes = HashMultimap.create();
		for (BlockPos pos : poses) {
			changes.put(new ChunkPos(pos), pos);
		}
		for (ChunkPos chunkPos : changes.keySet().toArray(new ChunkPos[changes.keySet().size()])) {
			Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
			byte[] biomeArray = chunk.getBiomeArray();
			Set<BlockPos> changeSet = changes.get(chunkPos);
			for (Iterator<BlockPos> iterator = changeSet.iterator(); iterator.hasNext(); ) {
				BlockPos pos = iterator.next();
				int i = pos.getX() & 15;
				int j = pos.getZ() & 15;
				if (biomeArray[j << 4 | i] == id) {
					iterator.remove();
				} else {
					biomeArray[j << 4 | i] = id;
				}
			}
		}
	}

	public static void resetRandomOverriddenBiome(World world) {
		ExtendedWorld extendedWorld = ExtendedWorld.get(world);
		BlockPos randomPos = extendedWorld.STORED_OVERRIDE_BIOMES.keySet().toArray(new BlockPos[extendedWorld.STORED_OVERRIDE_BIOMES.size()])[world.rand.nextInt(extendedWorld.STORED_OVERRIDE_BIOMES.size())];
		BiomeManipulator.setBiome(world, extendedWorld.STORED_OVERRIDE_BIOMES.get(randomPos), randomPos);
		extendedWorld.STORED_OVERRIDE_BIOMES.remove(randomPos);
		extendedWorld.setDirty(true);
	}


	public static void setBiome(World world, Biome biome, BlockPos pos) {
		Chunk chunk = world.getChunk(pos);

		int i = pos.getX() & 15;
		int j = pos.getZ() & 15;

		byte id = (byte) Biome.getIdForBiome(biome);

		byte b = chunk.getBiomeArray()[j << 4 | i];

		if (b == id) return;

		chunk.getBiomeArray()[j << 4 | i] = id;
		chunk.markDirty();
	}
}