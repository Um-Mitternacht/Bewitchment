package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.mp.IMagicPowerContainer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MagicPowerContainerStorage implements IStorage<IMagicPowerContainer> {

	@Override
	public NBTBase writeNBT(Capability<IMagicPowerContainer> capability, IMagicPowerContainer instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("amount", instance.getAmount());
		tag.setInteger("maxAmount", instance.getMaxAmount());
		return tag;
	}

	@Override
	public void readNBT(Capability<IMagicPowerContainer> capability, IMagicPowerContainer instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setMaxAmount(tag.getInteger("maxAmount"));
		instance.setAmount(tag.getInteger("amount"));
	}

}
