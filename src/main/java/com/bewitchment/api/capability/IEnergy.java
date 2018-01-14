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
	int getRegenTime();
	
	/**
	 * @return How much energy should be restored per energy regen tick
	 */
	int getRegenBurst();

	/**
	 * Sets the regen frequency.
	 *
	 * @param rateInSeconds The new regen frequency
	 * @param burst How much energy should be given per regen tick
	 */
	void setRegen(int rateInSeconds, int burst);

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

	/**
	 * Ticks the interanl counter and returns the result
	 *
	 * @return the amount of ticks passed
	 */
	int tick();

	/**
	 * @return the internal tick counter without increasing it
	 */
	int tickProgress();

	/**
	 * Resets the internal tick counter
	 */
	void tickReset();

	/**
	 * Sends a sync packet containing IEnergy information
	 *
	 * @param target the player you want to sync energy for
	 */
	void syncTo(EntityPlayerMP target);

	/**
	 * Gets the type of infusion/attunement this player has
	 *
	 * @return the corresponding EnumInfusionType
	 */
	EnumInfusionType getType();

	/**
	 * Sets the type of infusion/attunement for this player
	 *
	 * @param type the corresponding EnumInfusionType
	 */
	void setType(EnumInfusionType type);
}
