package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Optional;

public class RitualBiomeShift extends RitualImpl {


	public RitualBiomeShift(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	//This is used to proxy the array type, as it might be modified to an int array by mods like JeID
	private static void setArrayValue(Object array, int value, int index) {
		if (int[].class.isAssignableFrom(array.getClass())) {
			((int[]) array)[index] = value;
		} else {
			((byte[]) array)[index] = (byte) (value & 0xFF);
		}
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {

		covenSize = 4; //TODO use the actual coven size
		NonNullList<ItemStack> recipe = AdapterIRitual.getItemsUsedForInput(data);
		int id = Biome.getIdForBiome(Biomes.PLAINS);

		for (ItemStack is : recipe) {
			if (is.getItem() == ModItems.boline && is.hasTagCompound() && is.getTagCompound().hasKey("biome_id")) {
				id = is.getTagCompound().getInteger("biome_id");
			}
		}


		int distanceSq = 64 * (1 + 2 * covenSize) * (1 + 2 * covenSize);


		for (int dx = -covenSize; dx <= covenSize; dx++) {
			for (int dz = -covenSize; dz <= covenSize; dz++) {
				BlockPos chunkPos = effectivePosition.add(16 * dx, 0, 16 * dz);
				Chunk ch = world.getChunkFromBlockCoords(chunkPos);
				Object array = ch.getBiomeArray();
				for (int cx = 0; cx < 16; cx++) {
					for (int cz = 0; cz < 16; cz++) {
						int ax = ch.x * 16 + cx - effectivePosition.getX();
						int az = ch.z * 16 + cz - effectivePosition.getZ();
						if (az * az + ax * ax < distanceSq) {
							setArrayValue(array, id, cx + 16 * cz);
						}
					}
				}
				ch.markDirty();
				world.getMinecraftServer().getPlayerList().getPlayers().stream()
						.filter(p -> p.world.provider.getDimension() == world.provider.getDimension())
						.forEach(p -> p.connection.sendPacket(new SPacketChunkData(ch, 65535)));
				//TODO fix entities in the chunk becoming invisible
			}
		}


	}

	@Override
	public NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound data) {
		NonNullList<ItemStack> oldOutput = super.getOutput(input, data);
		Optional<ItemStack> oldBoline = input.parallelStream().filter(is -> is.getItem() == ModItems.boline).findFirst();
		if (oldBoline.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModItems.boline).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 50));
		}
		return oldOutput;
	}

}
