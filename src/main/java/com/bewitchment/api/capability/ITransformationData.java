package com.bewitchment.api.capability;

public interface ITransformationData {
	
	public EnumTransformationType getType();
	
	public void setType(EnumTransformationType type);
	
	public int getLevel();
	
	public void setLevel(int level);
	
	/**
	 * Use this to modify the blood in the vampire blood pool.
	 * 
	 * @exception UnsupportedOperationException
	 *                if the player is not a vampire
	 * @param amount
	 *            The amount of blood to add (negative values will decrease the total)
	 * @return
	 * 		<i>When adding</i> blood this will return true if the value changed and false otherwise: this is <b>true</b> if there was
	 *         even a little bit of space in the pool, but the blood added was greater than the amount that could be inserted,
	 *         and <b>false</b> if the pool was maxed.<br>
	 *         <i>When removing</i> blood this will return true if ALL the blood requested was drained.
	 *         If the amount drained is greater than the amount available this will return false, and no blood will be drained from the pool
	 */
	public boolean addVampireBlood(int amount);
	
	/**
	 * Gets the amount of blood in a vampire's pool
	 * 
	 * @return The amount of blood available
	 * @exception UnsupportedOperationException
	 *                if the player is not a vampire
	 */
	public int getBlood();
	
	/**
	 * Gets the maximum amount of blood that can be stored in a vampire's pool
	 * 
	 * @return The amount of blood that can be stored
	 * @exception UnsupportedOperationException
	 *                if the player is not a vampire
	 */
	public int getMaxBlood();
	
	/**
	 * Sets the amount of blood in a vampire's pool
	 * 
	 * @exception UnsupportedOperationException
	 *                if the player is not a vampire
	 */
	public void setBlood(int blood);
	
}
