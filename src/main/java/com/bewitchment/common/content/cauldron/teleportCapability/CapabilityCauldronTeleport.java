package com.bewitchment.common.content.cauldron.teleportCapability;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.HashMap;

public interface CapabilityCauldronTeleport {

	@CapabilityInject(CapabilityCauldronTeleport.class)
	public static final Capability<CapabilityCauldronTeleport> CAPABILITY = null;

	public static void init() {
		CapabilityManager.INSTANCE.register(CapabilityCauldronTeleport.class, new CauldronTeleportStorage(), Impl::new);
	}

	@Nullable
	public BlockPos get(String name, World world);

	public boolean put(World world, BlockPos position);

	public void writeToNBT(NBTTagCompound tag);

	public void readFromNBT(NBTTagCompound tag);

	public static class Impl implements CapabilityCauldronTeleport {

		private HashMap<String, BlockPos> map = new HashMap<>();

		@Override
		public boolean put(World world, BlockPos position) {
			if (world.getBlockState(position).getBlock() != ModBlocks.cauldron) {
				return false;
			}
			String cauldronName = ((TileEntityCauldron) world.getTileEntity(position)).getName();
			if (cauldronName == null) {
				return false;
			}

			cauldronName = cauldronName.replace(" ", "").trim();

			BlockPos pos = get(cauldronName, world);
			if (pos != null && !pos.equals(position)) {
				return false;
			}
			map.put(cauldronName, position);
			return true;
		}

		@Override
		@Nullable
		public BlockPos get(String name, World world) {
			name = name.replace(" ", "").trim();
			BlockPos position = map.get(name);
			if (position == null) {
				return null;
			}
			if (world.getBlockState(position).getBlock() != ModBlocks.cauldron) {
				return null;
			}

			if (!name.equals(((TileEntityCauldron) world.getTileEntity(position)).getName().replace(" ", "").trim())) {
				return null;
			}
			return map.get(name);
		}

		@Override
		public void writeToNBT(NBTTagCompound tag) {
			for (String name : map.keySet()) {
				NBTTagCompound entryTag = new NBTTagCompound();
				BlockPos pos = map.get(name);
				entryTag.setInteger("x", pos.getX());
				entryTag.setInteger("y", pos.getY());
				entryTag.setInteger("z", pos.getZ());
				tag.setTag(name, entryTag);
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound tag) {
			map = new HashMap<>();
			for (String name : tag.getKeySet()) {
				NBTTagCompound posTag = tag.getCompoundTag(name);
				BlockPos pos = new BlockPos(posTag.getInteger("x"), posTag.getInteger("y"), posTag.getInteger("z"));
				map.put(name, pos);
			}
		}
	}
}
