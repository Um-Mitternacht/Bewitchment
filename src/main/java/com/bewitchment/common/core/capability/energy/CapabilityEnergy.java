package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.capability.IEnergy;
import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EnergyMessage;
import com.bewitchment.common.infusion.ModInfusions;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class CapabilityEnergy {

	private static final String AMOUNT = "energy_amount";
	private static final String MAX = "energy_max_amount";
	private static final String REGEN_TIME = "energy_regen_rate";
	private static final String REGEN_BURST = "energy_regen_burst";
	private static final String USES = "energy_uses";
	private static final String TYPE = "energy_type";

	private CapabilityEnergy() {
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(IEnergy.class, new IStorage<IEnergy>() {

			@Override
			public NBTBase writeNBT(Capability<IEnergy> capability, IEnergy instance, EnumFacing side) {
				final NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger(AMOUNT, instance.get());
				tag.setInteger(MAX, instance.getMax());
				tag.setInteger(REGEN_TIME, instance.getRegenTime());
				tag.setInteger(REGEN_BURST, instance.getRegenBurst());
				tag.setInteger(USES, instance.getUses());
				tag.setString(TYPE, instance.getType().getRegistryName().toString());
				return tag;
			}

			@Override
			public void readNBT(Capability<IEnergy> capability, IEnergy instance, EnumFacing side, NBTBase nbt) {
				final NBTTagCompound tag = (NBTTagCompound) nbt;
				instance.set(tag.getInteger(AMOUNT));
				instance.setMax(tag.getInteger(MAX));
				instance.setRegen(tag.getInteger(REGEN_TIME), tag.getInteger(REGEN_BURST));
				instance.setUses(tag.getInteger(USES));
				instance.setType(ModInfusions.REGISTRY.getValue(new ResourceLocation(tag.getString(TYPE))));
			}
		}, DefaultEnergy::new);
	}

	public static class DefaultEnergy implements IEnergy {

		private int amount;
		private int max = 800;
		private int regenTime = 20;
		private int regenBurst = 10;
		private int uses;
		private int tick;
		private String type = DefaultInfusions.NONE.getRegistryName().toString();

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
		public int getRegenTime() {
			return regenTime;
		}

		@Override
		public void setRegen(int rate, int burst) {
			this.regenTime = rate > 0 ? rate : -1;
			this.regenBurst = burst;
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
			NetworkHandler.HANDLER.sendTo(new EnergyMessage(this, target), target);
		}

		@Override
		public IInfusion getType() {
			if (type == null) {
				return DefaultInfusions.NONE;
			}
			IInfusion inf = ModInfusions.REGISTRY.getValue(new ResourceLocation(type));
			if (inf == null) {
				return DefaultInfusions.NONE;
			}
			return inf;
		}

		@Override
		public void setType(IInfusion infusion) {
			if (infusion == null) {
				type = DefaultInfusions.NONE.getRegistryName().toString();
			} else {
				type = infusion.getRegistryName().toString();
			}
		}

		@Override
		public int tickProgress() {
			return tick;
		}

		@Override
		public int getRegenBurst() {
			return regenBurst;
		}
	}
}
