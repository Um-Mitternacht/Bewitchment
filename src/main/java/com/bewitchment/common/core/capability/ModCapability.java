package com.bewitchment.common.core.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public abstract class ModCapability implements INBTSerializable<NBTTagCompound> {
	public static <C extends ModCapability> void init(Class<C> capabilityClass) {
		CapabilityManager.INSTANCE.register(capabilityClass, new ModStorage<>(), capabilityClass::newInstance);
	}

	public static class ModStorage<C extends ModCapability> implements Capability.IStorage<C> {
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<C> capability, C instance, EnumFacing enumFacing) {
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<C> capability, C instance, EnumFacing enumFacing, NBTBase nbtBase) {
			instance.deserializeNBT((NBTTagCompound) nbtBase);
		}
	}
}
