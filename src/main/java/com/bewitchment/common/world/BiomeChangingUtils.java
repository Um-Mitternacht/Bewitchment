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
import org.dimdev.jeid.INewChunk;

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
					biomes[value] = (byte) id;
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

	public static void setBiome(World world, BlockPos pos, int id) {
		Chunk chunk = world.getChunk(pos);

		int x = pos.getX() & 15;
		int y = pos.getZ() & 15;
		int i = y << 4 | x;

		if (Bewitchment.JEID && chunk instanceof INewChunk) {
			((INewChunk)chunk).getIntBiomeArray()[i] = id;
		} else {
			chunk.getBiomeArray()[i] = (byte)id;
		}

		if (!world.isRemote) chunk.markDirty();
	}

	public static void refresh(World world, BlockPos start, int radius) {
		if (world.isRemote) world.markBlockRangeForRenderUpdate(
				start.add(-radius, -radius, -radius),
				start.add(radius, radius, radius));
	}

	@Deprecated
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