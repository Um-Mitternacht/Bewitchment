package com.bewitchment.api.capability;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface IEnergy {

	/**
	 * Sets the magic amount.
	 *
	 * @param amount The new amount
	 * @return If the amount doesn't exceed max amount
	 */
	boolean set(int amount);

	/**
	 * Returns the current amount.
	 *
	 * @return The energy amount
	 */
	int get();

	/**
	 * Returns the max energy amount.
	 *
	 * @return The max energy amount
	 */
	int getMax();

	/**
	 * Sets the max energy amount.
	 *
	 * @param max The new max amount
	 */
	void setMax(int max);

	/**
	 * Returns the regen frequency.
	 *
	 * @return The frequency
	 */
	int getRegen();

	/**
	 * Sets the regen frequency.
	 *
	 * @param rateInSeconds The new regen frequency
	 */
	void setRegen(int rateInSeconds);

	/**
	 * Returns the amount of times energy has been used.
	 *
	 * @return The uses
	 */
	int getUses();

	/**
	 * Sets the number amount of times energy has been used.
	 *
	 * @param uses The new amount of uses
	 */
	void setUses(int uses);

	int tick();

	void tickReset();

	void syncTo(EntityPlayerMP target);
}
