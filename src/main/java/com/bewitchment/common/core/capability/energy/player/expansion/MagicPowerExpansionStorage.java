package com.bewitchment.common.core.capability.energy.player.expansion;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MagicPowerExpansionStorage implements IStorage<CapabilityMPExpansion> {

	@Override
	public NBTBase writeNBT(Capability<CapabilityMPExpansion> capability, CapabilityMPExpansion instance, EnumFacing side) {
		return instance.writeToNBT();
	}

	@Override
	public void readNBT(Capability<CapabilityMPExpansion> capability, CapabilityMPExpansion instance, EnumFacing side, NBTBase nbt) {
		instance.readFromNBT((NBTTagCompound) nbt);
	}

}
