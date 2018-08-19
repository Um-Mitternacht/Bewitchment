package com.bewitchment.api.mp;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IMagicPowerUsingItem {
	/**
	 * This class is only here to make items that have this capability show the MP bar. No additional content required
	 */
	@CapabilityInject(IMagicPowerUsingItem.class)
	public static final Capability<IMagicPowerUsingItem> CAPABILITY = null;

	public static class Storage implements IStorage<IMagicPowerUsingItem> {

		@Override
		public NBTBase writeNBT(Capability<IMagicPowerUsingItem> capability, IMagicPowerUsingItem instance, EnumFacing side) {
			return new NBTTagCompound();
		}

		@Override
		public void readNBT(Capability<IMagicPowerUsingItem> capability, IMagicPowerUsingItem instance, EnumFacing side, NBTBase nbt) {
		}

	}
}
