package com.bewitchment.api.mp;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * This class handles MP storage and MP transfer of any object that should
 * have an internal power meter.
 * (by default implemented for Altar Tile Entities and Player Entities)
 *
 * @author zabi94
 */
public interface IMagicPowerContainer {

	@CapabilityInject(IMagicPowerContainer.class)
	public static final Capability<IMagicPowerContainer> CAPABILITY = null;

	public int getAmount();

	public int getMaxAmount();

	/**
	 * Use this to to fill a magic power destination. When returning false no modification has been made
	 * to the filled entity.
	 *
	 * @param amount the amount of power to add to the source. Amount MUST be >=0.
	 * @return false if the object was already at max power, true otherwise.
	 */
	public boolean fill(int amount);

	/**
	 * Use this to drain power from a source. When returning false no modification has been made
	 * to the drained entity.
	 *
	 * @param amount the amount of power requested. Amount MUST be >=0.
	 * @return false if there was not enough energy, true otherwise.
	 */
	public boolean drain(int amount);

	/**
	 * Prefer the use of {@link #fill(int)} and {@link #drain(int)} to this.
	 * Sets the current power amount to the specified amount. If the amount is greater
	 * than the maximum amount a {@link MPManipulationException} is raised.
	 *
	 * @param amount the amount to set the power to. Must be greater or equal to 0.
	 */
	public void setAmount(int amount);

	/**
	 * Sets the current power maximum amount to the specified amount. If the amount is lower
	 * than the current power level, the power level is decreased to the new maximum.
	 *
	 * @param amount the amount to set the power to. Must be greater or equal to 0.
	 */
	public void setMaxAmount(int amount);

	@SuppressWarnings("serial")
	public static class MPManipulationException extends RuntimeException {

		public MPManipulationException(String string) {
			super(string);
		}
	}

}
