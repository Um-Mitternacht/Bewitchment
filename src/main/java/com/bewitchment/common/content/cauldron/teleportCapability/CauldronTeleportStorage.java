package com.bewitchment.common.content.cauldron.teleportCapability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CauldronTeleportStorage implements IStorage<CapabilityCauldronTeleport> {

	@Override
	public NBTBase writeNBT(Capability<CapabilityCauldronTeleport> capability, CapabilityCauldronTeleport instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		instance.writeToNBT(tag);
		return tag;
	}

	@Override
	public void readNBT(Capability<CapabilityCauldronTeleport> capability, CapabilityCauldronTeleport instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.readFromNBT(tag);
	}

}
