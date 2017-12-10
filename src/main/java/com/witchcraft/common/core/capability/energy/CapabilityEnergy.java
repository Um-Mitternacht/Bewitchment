package com.witchcraft.common.core.capability.energy;

import com.witchcraft.api.capability.IEnergy;
import com.witchcraft.common.core.net.EnergyMessage;
import com.witchcraft.common.core.net.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class CapabilityEnergy {

	private static final String AMOUNT = "energy_amount";
	private static final String MAX = "energy_max";
	private static final String REGEN = "energy_regen";
	private static final String USES = "energy_uses";

	private CapabilityEnergy() {
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(IEnergy.class, new IStorage<IEnergy>() {

			@Override
			public NBTBase writeNBT(Capability<IEnergy> capability, IEnergy instance, EnumFacing side) {
				final NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger(AMOUNT, instance.get());
				tag.setInteger(MAX, instance.getMax());
				tag.setInteger(REGEN, instance.getRegen());
				tag.setInteger(USES, instance.getUses());
				return tag;
			}

			@Override
			public void readNBT(Capability<IEnergy> capability, IEnergy instance, EnumFacing side, NBTBase nbt) {
				final NBTTagCompound tag = (NBTTagCompound) nbt;
				instance.set(tag.getInteger(AMOUNT));
				instance.setMax(tag.getInteger(MAX));
				instance.setRegen(tag.getInteger(REGEN));
				instance.setUses(tag.getInteger(USES));
			}
		}, DefaultEnergy::new);
	}

	public static class DefaultEnergy implements IEnergy {

		private int amount;
		private int max = 8;
		private int regen = 60;
		private int uses;
		private int tick;

		@Override
		public boolean set(int i) {
			this.amount = i;
			if (this.amount > getMax())
				this.amount = getMax();
			if (this.amount < 0)
				this.amount = 0;

			return i >= 0 && i <= getMax();
		}

		@Override
		public int get() {
			return amount;
		}

		@Override
		public int getMax() {
			return max;
		}

		@Override
		public void setMax(int max) {
			this.max = max;
		}

		@Override
		public int getRegen() {
			return regen;
		}

		@Override
		public void setRegen(int rate) {
			this.regen = rate > 0 ? rate : -1;
		}

		@Override
		public int getUses() {
			return uses;
		}

		@Override
		public void setUses(int uses) {
			this.uses = uses >= 0 ? uses : 0;
		}

		@Override
		public int tick() {
			return ++tick;
		}

		@Override
		public void tickReset() {
			this.tick = 0;
		}

		@Override
		public void syncTo(EntityPlayerMP target) {
			PacketHandler.HANDLER.sendTo(new EnergyMessage(this, target), target);
		}
	}
}
