package com.bewitchment.common.core.capability.energy.storage;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.common.core.capability.ModCapability;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EnergyMessage;
import com.bewitchment.common.infusion.ModInfusions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class CapabilityMagicPoints extends ModCapability {
	private static final String AMOUNT_TAG = "energy_amount";
	private static final String MAX_TAG = "energy_max_amount";
	private static final String REGEN_TIME_TAG = "energy_regen_rate";
	private static final String REGEN_BURST = "energy_regen_burst";
	private static final String USES = "energy_uses";
	private static final String TYPE = "energy_type";

	@CapabilityInject(CapabilityMagicPoints.class)
	public static final Capability<CapabilityMagicPoints> CAPABILITY = null;

	private int amount;
	private int max = 800;
	private int regenTime = 20;
	private int regenBurst = 10;
	private int uses;
	private int tick;
	private IInfusion type = DefaultInfusions.NONE;

	public CapabilityMagicPoints() {

	}

	
	public boolean hasPoints(int amount) {
		return amount <= this.amount;
	}

	
	public boolean subtract(int amount) {
		if(hasPoints(amount)) {
			this.amount -= amount;
			return true;
		}
		return false;
	}

	
	public void add(int amount) {
		this.amount = Math.min(max, this.amount + amount);
	}

	
	public boolean set(int i) {
		this.amount = i;
		if (this.amount > getMax())
			this.amount = getMax();
		if (this.amount < 0)
			this.amount = 0;

		return i >= 0 && i <= getMax();
	}

	
	public int get() {
		return amount;
	}

	
	public int getMax() {
		return max;
	}

	
	public void setMax(int max) {
		this.max = max;
	}

	
	public int getRegenTime() {
		return regenTime;
	}

	
	public void setRegen(int rate, int burst) {
		this.regenTime = rate > 0 ? rate : -1;
		this.regenBurst = burst;
	}

	
	public int getUses() {
		return uses;
	}

	
	public void setUses(int uses) {
		this.uses = uses >= 0 ? uses : 0;
	}

	
	public int tick() {
		return ++tick;
	}

	
	public void tickReset() {
		this.tick = 0;
	}

	
	public void syncTo(EntityPlayerMP target) {
		NetworkHandler.HANDLER.sendTo(new EnergyMessage(this, target), target);
	}

	
	public IInfusion getType() {
		//TODO: clean
		if (type == null) {
			return DefaultInfusions.NONE;
		}
		IInfusion inf = type;
		if (inf == null) {
			return DefaultInfusions.NONE;
		}
		return inf;
	}

	
	public void setType(IInfusion infusion) {
		if (infusion == null) {
			type = DefaultInfusions.NONE;
		} else {
			type = infusion;
		}
	}

	
	public int tickProgress() {
		return tick;
	}

	
	public int getRegenBurst() {
		return regenBurst;
	}

	
	@Override
	public NBTTagCompound serializeNBT() {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(AMOUNT_TAG, get());
		tag.setInteger(MAX_TAG, getMax());
		tag.setInteger(REGEN_TIME_TAG, getRegenTime());
		tag.setInteger(REGEN_BURST, getRegenBurst());
		tag.setInteger(USES, getUses());
		tag.setString(TYPE, getType().getRegistryName().toString());
		return tag;
	}

	
	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		this.set(tag.getInteger(AMOUNT_TAG));
		this.setMax(tag.getInteger(MAX_TAG));
		this.setRegen(tag.getInteger(REGEN_TIME_TAG), tag.getInteger(REGEN_BURST));
		this.setUses(tag.getInteger(USES));
		this.setType(ModInfusions.REGISTRY.getValue(new ResourceLocation(tag.getString(TYPE))));
	}
}
