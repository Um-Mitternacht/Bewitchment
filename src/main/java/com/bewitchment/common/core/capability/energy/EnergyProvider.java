package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.capability.IEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
@SuppressWarnings("ConstantConditions")
public class EnergyProvider implements ICapabilitySerializable<NBTTagCompound> {

	@CapabilityInject(IEnergy.class)
	public static final Capability<IEnergy> ENERGY_CAPABILITY = null;
	private final IEnergy defaultEnergy = new CapabilityEnergy.DefaultEnergy();

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return ENERGY_CAPABILITY == capability;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return ENERGY_CAPABILITY == capability ? ENERGY_CAPABILITY.cast(defaultEnergy) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) ENERGY_CAPABILITY.writeNBT(defaultEnergy, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		ENERGY_CAPABILITY.readNBT(defaultEnergy, null, nbt);
	}
}
