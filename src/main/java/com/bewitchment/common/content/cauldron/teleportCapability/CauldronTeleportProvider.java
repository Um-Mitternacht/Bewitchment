package com.bewitchment.common.content.cauldron.teleportCapability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CauldronTeleportProvider implements ICapabilitySerializable<NBTBase> {

	private CapabilityCauldronTeleport default_capability = CapabilityCauldronTeleport.CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityCauldronTeleport.CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityCauldronTeleport.CAPABILITY)
			return CapabilityCauldronTeleport.CAPABILITY.<T>cast(default_capability);
		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return CapabilityCauldronTeleport.CAPABILITY.getStorage().writeNBT(CapabilityCauldronTeleport.CAPABILITY, default_capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		CapabilityCauldronTeleport.CAPABILITY.getStorage().readNBT(CapabilityCauldronTeleport.CAPABILITY, default_capability, null, nbt);
	}

}