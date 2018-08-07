package com.bewitchment.api.mp;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Add one of these in any object that hold MP for an easy solution
 *
 * @author zabi94
 */
public class DefaultMPContainer implements IMagicPowerContainer {

	int amount = 0;
	int maxAmount;

	public DefaultMPContainer(int max) {
		maxAmount = max;
		amount = max;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public int getMaxAmount() {
		return maxAmount;
	}

	@Override
	public boolean fill(int in) {
		if (getAmount() < getMaxAmount()) {
			setAmount(Math.min(getAmount() + in, getMaxAmount()));
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
		maxAmount = newMaxAmount;
		if (getAmount() > maxAmount) {
			setAmount(maxAmount);
		}
	}

	public NBTTagCompound saveNBTTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("current", getAmount());
		tag.setInteger("max", getMaxAmount());
		return tag;
	}

	public void loadFromNBT(NBTTagCompound tag) {
		setMaxAmount(tag.getInteger("max"));
		setAmount(tag.getInteger("current"));
	}

}
