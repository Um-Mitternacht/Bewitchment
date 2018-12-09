package com.bewitchment.common.world;

import com.bewitchment.api.BewitchmentAPI;
import com.google.common.collect.Sets;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class BiomeChangingUtils {

	public static void changeWholeBiome(BlockPos pos, Biome newBiome, World world) {
		int id = Biome.getIdForBiome(newBiome);
		Chunk ch = world.getChunk(pos);
		Object array = ch.getBiomeArray();
		for (int i = 0; i < 255; i++) setArrayValue(array, id, i);
		ch.markDirty();
		refreshChunk(ch);
	}


	//This is used to proxy the array type, as it might be modified to an int array by mods like JeID
	private static boolean setArrayValue(Object array, int value, int index) {
		if (int[].class.isAssignableFrom(array.getClass())) {
			if (((int[]) array)[index] == value) {
				return false;
			}
			((int[]) array)[index] = value;
			return true;
		}
		if (((byte[]) array)[index] == (byte) (value & 0xFF)) {
			return false;
		}
		((byte[]) array)[index] = (byte) (value & 0xFF);
		return true;
	}

	//TODO fix entities in the chunk becoming invisible
	private static void refreshChunk(Chunk ch) {
		ch.getWorld().getMinecraftServer().getPlayerList().getPlayers().stream()
				.forEach(p -> p.connection.sendPacket(new SPacketChunkData(ch, 65535)));
	}

	public static class BiomeChangerWalker {

		private boolean complete = false;
		private int biomeId;
		private Set<Chunk> chunkSet = Sets.<Chunk>newHashSet();

		/**
		 * Use this class to efficiently change biome to multiple block coords spanning across multiple chunks.
		 * <br><br>Use {@link #visit(Chunk, BlockPos)} or {@link #visit(World, BlockPos)} to add blocks to change, using
		 * MutableBlockPos for efficiency when too many.
		 * <br><br>When done adding coords use {@link #complete()} to complete the process. Don't leave a walker that has
		 * visited at least a coordinate hanging, as it may cause desyncs and random weirdness.
		 * <br><br>Biomes with the BiomeDict tag IMMUTABLE won't be converted unless using the {@link #visitImmutable(Chunk, BlockPos)}
		 * and {@link #visitImmutable(World, BlockPos)} methods.
		 *
		 * @param destinationBiome What biome to convert blocks to
		 * @see BewitchmentAPI#IMMUTABLE
		 */
		public BiomeChangerWalker(Biome destinationBiome) {
			this(Biome.getIdForBiome(destinationBiome));
		}

		/**
		 * Use this class to efficiently change biome to multiple block coords spanning across multiple chunks.
		 * <br><br>Use {@link #visit(Chunk, BlockPos)} or {@link #visit(World, BlockPos)} to add blocks to change, using
		 * MutableBlockPos for efficiency when too many.
		 * <br><br>When done adding coords use {@link #complete()} to complete the process. Don't leave a walker that has
		 * visited at least a coordinate hanging, as it may cause desyncs and random weirdness.
		 * <br><br>Biomes with the BiomeDict tag IMMUTABLE won't be converted unless using the {@link #visitImmutable(Chunk, BlockPos)}
		 * and {@link #visitImmutable(World, BlockPos)} methods.
		 *
		 * @param destinationBiomeId What biome ID to convert blocks to
		 * @see BewitchmentAPI#IMMUTABLE
		 */
		public BiomeChangerWalker(int destinationBiomeId) {
			if (destinationBiomeId < 0) {
				throw new IllegalArgumentException("Biome ID must be positive, it was " + destinationBiomeId);
			}
			biomeId = destinationBiomeId;
		}

		/**
		 * Changes a biome column in the chunk. Won't change biomes that have the IMMUTABLE dict type
		 *
		 * @param ch  The chunk the column belongs to
		 * @param pos The position of the column. Y value is ignored
		 * @return true if the chunk changed, false otherwise
		 * @throws IllegalStateException if invoked after being marked as {@link #complete()}
		 * @see BewitchmentAPI#IMMUTABLE
		 */
		public boolean visit(Chunk ch, BlockPos pos) {
			if (BiomeDictionary.hasType(ch.getWorld().getBiome(pos), BewitchmentAPI.getAPI().IMMUTABLE)) {
				return false;
			}
			return visitImmutable(ch, pos);
		}

		/**
		 * Changes a biome column in the world. Won't change biomes that have the IMMUTABLE dict type
		 *
		 * @param world The world the column belongs to
		 * @param pos   The position of the column. Y value is ignored
		 * @return true if the chunk changed, false otherwise
		 * @throws IllegalStateException if invoked after being marked as {@link #complete()}
		 * @see BewitchmentAPI#IMMUTABLE
		 */
		public boolean visit(World world, BlockPos pos) {
			return visit(world.getChunk(pos), pos);
		}

		/**
		 * Changes a biome column in the chunk. Will also change biomes that have the IMMUTABLE dict type
		 *
		 * @param ch  The chunk the column belongs to
		 * @param pos The position of the column. Y value is ignored
		 * @return true if the chunk changed, false otherwise
		 * @throws IllegalStateException if invoked after being marked as {@link #complete()}
		 * @see BewitchmentAPI#IMMUTABLE
		 */
		public boolean visitImmutable(Chunk ch, BlockPos pos) {
			if (complete) {
				throw new IllegalStateException("Cannot walk after completion!");
			}
			chunkSet.add(ch);
			return setArrayValue(ch.getBiomeArray(), biomeId, (pos.getX() - ch.x * 16) + (16 * (pos.getZ() - ch.z * 16)));
		}

		/**
		 * Changes a biome column in the world. Will also change biomes that have the IMMUTABLE dict type
		 *
		 * @param world The world the column belongs to
		 * @param pos   The position of the column. Y value is ignored
		 * @return true if the chunk changed, false otherwise
		 * @throws IllegalStateException if invoked after being marked as {@link #complete()}
		 * @see BewitchmentAPI#IMMUTABLE
		 */
		public boolean visitImmutable(World world, BlockPos pos) {
			return visitImmutable(world.getChunk(pos), pos);
		}

		/**
		 * This method will mark all the chunks interested by the instance as dirty and will synchronize them to
		 * the clients in the dimension. Any following invocation to other methods from this class will likely
		 * result in an exception.
		 * <br><br>Don't forget to call this if this walker has used any visit or visitImmutable methods
		 *
		 * @throws IllegalStateException if called twice on the same object instance
		 */
		public void complete() {
			if (complete) {
				throw new IllegalStateException("Cannot complete the same walker multiple times!");
			}
			complete = true;
			for (Chunk ch : chunkSet) {
				ch.markDirty();
				refreshChunk(ch);
			}
		}
	}
}
