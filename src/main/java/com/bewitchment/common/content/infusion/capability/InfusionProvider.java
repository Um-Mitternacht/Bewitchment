package com.bewitchment.common.content.infusion.capability;

import com.bewitchment.api.infusion.IInfusionCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class InfusionProvider implements ICapabilitySerializable<NBTBase> {

	private IInfusionCapability default_capability = InfusionCapability.CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == InfusionCapability.CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == InfusionCapability.CAPABILITY) {
			return InfusionCapability.CAPABILITY.cast(default_capability);
		}
		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return InfusionCapability.CAPABILITY.getStorage().writeNBT(InfusionCapability.CAPABILITY, default_capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		InfusionCapability.CAPABILITY.getStorage().readNBT(InfusionCapability.CAPABILITY, default_capability, null, nbt);
	}

}
