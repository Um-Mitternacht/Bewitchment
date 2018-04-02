package com.bewitchment.api.cauldron;

public interface IBrewModifierList {
	
	public static final int NOT_PRESENT = Integer.MAX_VALUE;
	
	/**
	 * @param modifier The modifier you want to learn the level of
	 * @return The level (whatever integer-representable data you wish) of the modifier, or {@link #NOT_PRESENT} if it's not in list
	 */
	public int getLevel(IBrewModifier modifier);
	
}
