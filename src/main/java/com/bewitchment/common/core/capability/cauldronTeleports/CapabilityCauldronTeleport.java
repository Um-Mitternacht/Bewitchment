package com.bewitchment.common.core.capability.cauldronTeleports;

import java.util.HashMap;

import javax.annotation.Nullable;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.tile.TileEntityCauldron;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public interface CapabilityCauldronTeleport {

	@CapabilityInject(CapabilityCauldronTeleport.class)
	public static final Capability<CapabilityCauldronTeleport> CAPABILITY = null;

	public static void init() {
		CapabilityManager.INSTANCE.register(CapabilityCauldronTeleport.class, new CauldronTeleportStorage(), Impl::new);
	}

	public boolean put(World world, BlockPos position);
	
	@Nullable
	public BlockPos get(String name, World world);
	
	public void writeToNBT(NBTTagCompound tag);
	
	public void readFromNBT(NBTTagCompound tag);

	public static class Impl implements CapabilityCauldronTeleport {
		
		private static HashMap<Integer, HashMap<String, BlockPos>> map = new HashMap<>();

		@Override
		public boolean put(World world, BlockPos position) {
			HashMap<String, BlockPos> dimMap = map.get(world.provider.getDimension());
			if (dimMap == null) {
				dimMap = new HashMap<String, BlockPos>();
				map.put(world.provider.getDimension(), dimMap);
			}
			
			if (world.getBlockState(position).getBlock() != ModBlocks.cauldron) {
				System.out.println("Not a cauldron");
				return false;
			}
			
			String cauldronName = ((TileEntityCauldron) world.getTileEntity(position)).getName();
			
			if (cauldronName == null) {
				System.out.println("Invalid cauldron name");
				return false;
			}
			
			BlockPos pos = get(cauldronName, world);
			
			if (pos != null && !pos.equals(position)) {
				System.out.println("Name exists already");
				return false;
			}
			
			dimMap.put(cauldronName, position);
			return true;
		}

		@Override
		@Nullable
		public BlockPos get(String name, World world) {
			HashMap<String, BlockPos> dimMap = map.get(world.provider.getDimension());
			if (dimMap == null) {
				dimMap = new HashMap<String, BlockPos>();
				map.put(world.provider.getDimension(), dimMap);
			}
			
			BlockPos position = dimMap.get(name);
			
			if (position == null) {
				System.out.println("No cached position");
				return null;
			}
			
			if (world.getBlockState(position).getBlock() != ModBlocks.cauldron) {
				System.out.println("No cauldron found");
				return null;
			}
			
			if (!name.equals(((TileEntityCauldron) world.getTileEntity(position)).getName())) {
				System.out.println("Wrong cauldron found");
				return null;
			}
			
			return dimMap.get(name);
		}
		
		@Override
		public void writeToNBT(NBTTagCompound tag) {
			// System.out.println("Writing map to NBT:");
			for (int dim : map.keySet()) {
				// System.out.println("\tDim " + dim);
				NBTTagCompound dimTag = new NBTTagCompound();
				HashMap<String, BlockPos> dimMap = map.get(dim);
				for (String name : dimMap.keySet()) {
					NBTTagCompound entryTag = new NBTTagCompound();
					BlockPos pos = dimMap.get(name);
					entryTag.setInteger("x", pos.getX());
					entryTag.setInteger("y", pos.getY());
					entryTag.setInteger("z", pos.getZ());
					dimTag.setTag(name, entryTag);
					// System.out.println("\t\t" + name + " - " + pos);
				}
				tag.setTag("" + dim, dimTag);
			}
			// System.out.println("Result: " + tag);
		}
		
		@Override
		public void readFromNBT(NBTTagCompound tag) {
			map = new HashMap<>();
			// System.out.println("Loading map from NBT:");
			for (String dimString : tag.getKeySet()) {
				// System.out.println("\t" + dimString);
				int dim = Integer.parseInt(dimString);
				HashMap<String, BlockPos> dimMap = new HashMap<>();
				NBTTagCompound dimtag = tag.getCompoundTag(dimString);
				
				for (String name : dimtag.getKeySet()) {
					NBTTagCompound posTag = dimtag.getCompoundTag(name);
					BlockPos pos = new BlockPos(posTag.getInteger("x"), posTag.getInteger("y"), posTag.getInteger("z"));
					// System.out.println("\t\t" + name + " - " + pos);
					dimMap.put(name, pos);
				}
				
				map.put(dim, dimMap);
			}
		}
		
	}

}
