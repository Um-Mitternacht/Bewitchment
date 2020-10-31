package com.bewitchment.common.world;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.network.PacketChangeBiome;
import com.google.common.collect.HashMultimap;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;
import java.util.Set;

/**
 * Original code created by Ael.
 */
public class BiomeChangingUtils {


	public static void setMultiBiome(World world, Biome biome, BlockPos... poses) {
		int id = Biome.getIdForBiome(biome); // id of the biome to change to

		HashMultimap<ChunkPos, BlockPos> changes = HashMultimap.create();

		for (BlockPos pos : poses) changes.put(new ChunkPos(pos), pos);

		changes.keys().forEach(chunkPos -> {

			Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
			byte[] biomes = chunk.getBiomeArray();

			Set<BlockPos> changeSet = changes.get(chunkPos);

			changeSet.forEach(pos -> {
				int i = pos.getX() & 15;
				int j = pos.getZ() & 15;

				int value = j << 4 | i;

				if (biomes[value] == id) {
					changeSet.remove(pos);
				} else {
					biomes[value] = (byte)id;
				}
			});
			chunk.markDirty();
		});
	}

	public static void resetRandomOverriddenBiome(World world) {
		ExtendedWorld extendedWorld = ExtendedWorld.get(world);
		BlockPos randomPos = extendedWorld.STORED_OVERRIDE_BIOMES.keySet().toArray(new BlockPos[extendedWorld.STORED_OVERRIDE_BIOMES.size()])[world.rand.nextInt(extendedWorld.STORED_OVERRIDE_BIOMES.size())];
		BiomeChangingUtils.setBiome(world, extendedWorld.STORED_OVERRIDE_BIOMES.get(randomPos), randomPos);
		extendedWorld.STORED_OVERRIDE_BIOMES.remove(randomPos);
		extendedWorld.setDirty(true);
	}

	//WIP modify if needed. Currently changes every biome in a chunk
	public static void setBiome(World world, Chunk chunk, BlockPos pos, Biome biome) {
		int biomeId = Biome.getIdForBiome(biome); // Get the Biome Id.

		/**
		 * int array will accept byte. issue is casting byte[] to int[]
		 * could have a if statement JEID is "installed" to use alternative method
		 */
		Arrays.fill(chunk.getBiomeArray(), (byte)biomeId);

		//Update changes
		if (!world.isRemote) chunk.markDirty();
		else world.markBlockRangeForRenderUpdate(pos.add(-10, 0, -10), pos.add(10, 0, 10));
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

		if (world instanceof WorldServer) {
			PlayerChunkMap playerChunkMap = ((WorldServer) world).getPlayerChunkMap();
			int chunkX = pos.getX() >> 4;
			int chunkZ = pos.getZ() >> 4;

			PlayerChunkMapEntry entry = playerChunkMap.getEntry(chunkX, chunkZ);
			if (entry != null) {
				Bewitchment.network.sendToAll(new PacketChangeBiome(biome, pos));
			}
		}
	}
}