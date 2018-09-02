package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MagicPowerConsumerStorage implements IStorage<IMagicPowerConsumer> {

	@Override
	public NBTBase writeNBT(Capability<IMagicPowerConsumer> capability, IMagicPowerConsumer instance, EnumFacing side) {
		return instance.writeToNbt();
	}

	@Override
	public void readNBT(Capability<IMagicPowerConsumer> capability, IMagicPowerConsumer instance, EnumFacing side, NBTBase nbt) {
		instance.readFromNbt((NBTTagCompound) nbt);
	}

}
