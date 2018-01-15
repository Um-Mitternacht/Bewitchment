package com.bewitchment.common.core.capability.energy.energy_item;

import com.bewitchment.api.capability.IItemEnergyUser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityEnergyUser {

	private CapabilityEnergyUser() {
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(IItemEnergyUser.class, new IStorage<IItemEnergyUser>() {

			@Override
			public NBTBase writeNBT(Capability<IItemEnergyUser> capability, IItemEnergyUser instance, EnumFacing side) {
				return new NBTTagCompound();
			}

			@Override
			public void readNBT(Capability<IItemEnergyUser> capability, IItemEnergyUser instance, EnumFacing side, NBTBase nbt) {
			}
		}, DefImplItemEnergyUser::new);
	}

	public static class DefImplItemEnergyUser implements IItemEnergyUser {

		protected DefImplItemEnergyUser() {
		}

		@Override
		public boolean shouldShowHud() {
			return true; // If an item has this capability, it probably wants to show hud whenever the player is holding it
		}
	}
}
