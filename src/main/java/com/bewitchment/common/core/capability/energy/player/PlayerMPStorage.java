package com.bewitchment.common.core.capability.energy.player;

import java.lang.ref.WeakReference;

import com.bewitchment.api.mp.IMagicPowerStorage;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerMPStorage implements IMagicPowerStorage {
	
	WeakReference<EntityPlayer> player;
	int amount = 0, baseMaxAmount = 800;
	
	public PlayerMPStorage(EntityPlayer p) {
		player = new WeakReference<EntityPlayer>(p);
	}

	@Override
	public int getAmount() {
		return amount;
	}
	
	@Override
	public int getMaxAmount() {
		return baseMaxAmount + getExtra();
	}
	
	private int getExtra() {
		// TODO calculate bonus from herbs eaten, worn baubles, potion effects and what not
		return 0;
	}
	
	@Override
	public boolean fill(int in) {
		if (getAmount() < getMaxAmount()) {
			setAmount(getAmount() + in);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean drain(int out) {
		if (getAmount() >= out) {
			setAmount(getAmount() - out);
			return true;
		}
		return false;
	}
	
	@Override
	public void setAmount(int newAmount) {
		if (newAmount > getMaxAmount()) {
			throw new MPManipulationException(String.format("The amount set (%d) is greater than the maximum amount (%d)", newAmount, getMaxAmount()));
		}
		if (newAmount < 0) {
			throw new MPManipulationException("The amount must be 0 or greater");
		}
		amount = newAmount;
	}
	
	@Override
	public void setMaxAmount(int newMaxAmount) {
		if (newMaxAmount < 0) {
			throw new MPManipulationException("The amount must be 0 or greater");
		}
		baseMaxAmount = newMaxAmount;
		if (getAmount() > baseMaxAmount) {
			setAmount(baseMaxAmount);
		}
	}
	
}
