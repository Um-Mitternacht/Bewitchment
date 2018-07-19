package com.bewitchment.common.core.capability.energy.storage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagicPointsProvider implements ICapabilitySerializable<NBTBase> {
	private CapabilityMagicPoints default_capability = CapabilityMagicPoints.CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing) {
		return capability == CapabilityMagicPoints.CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
		if (capability == CapabilityMagicPoints.CAPABILITY) {
			return CapabilityMagicPoints.CAPABILITY.cast(default_capability);
		}
		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return CapabilityMagicPoints.CAPABILITY.getStorage().writeNBT(CapabilityMagicPoints.CAPABILITY, default_capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbtBase) {
		CapabilityMagicPoints.CAPABILITY.getStorage().readNBT(CapabilityMagicPoints.CAPABILITY, default_capability, null, nbtBase);
	}
}
