package com.bewitchment.api.cauldron;

import java.util.Set;

/**
 * This is not meant to be implemented by other mods, just use the provided one
 * 
 * @author zabi9
 */
public interface IBrewModifierList {
	
	public static final int NOT_PRESENT = Integer.MAX_VALUE;
	
	/**
	 * @param modifier The modifier you want to learn the level of
	 * @return The level (whatever integer-representable data you wish) of the modifier, or {@link #NOT_PRESENT} if it's not in list
	 */
	public int getLevel(IBrewModifier modifier);
	
	public Set<IBrewModifier> getModifiers();
}
