package com.bewitchment.common.core.capability.energy.player;

import com.bewitchment.api.mp.IMagicPowerContainer;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerMPContainerProvider implements ICapabilitySerializable<NBTBase> {
	
	private IMagicPowerContainer default_capability = IMagicPowerContainer.CAPABILITY.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == IMagicPowerContainer.CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == IMagicPowerContainer.CAPABILITY) {
			return IMagicPowerContainer.CAPABILITY.cast(default_capability);
		}
		return null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		return IMagicPowerContainer.CAPABILITY.getStorage().writeNBT(IMagicPowerContainer.CAPABILITY, default_capability, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {
		IMagicPowerContainer.CAPABILITY.getStorage().readNBT(IMagicPowerContainer.CAPABILITY, default_capability, null, nbt);
	}
	
}
