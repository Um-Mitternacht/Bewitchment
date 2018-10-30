package com.bewitchment.common.content.transformation.capability;

import com.bewitchment.api.transformation.ITransformation;

public interface ITransformationData {

	public ITransformation getType();

	/**
	 * Internal use only. Use the API's setTypeAndLevel
	 */
	@Deprecated
	public void setType(ITransformation type);

	public int getLevel();
	
	public boolean needsSync();
	
	public void markAsSynced();

	/**
	 * Internal use only. Use the API's setTypeAndLevel
	 */
	@Deprecated
	public void setLevel(int level);

	/**
	 * Internal use only. Use the API's addVampireBlood instead to keep it syncronized
	 *
	 * @param amount The amount of blood to add (negative values will decrease the total)
	 * @return <i>When adding</i> blood this will return true if the value changed and false otherwise: this is <b>true</b> if there was
	 * even a little bit of space in the pool, but the blood added was greater than the amount that could be inserted,
	 * and <b>false</b> if the pool was maxed.<br>
	 * <i>When removing</i> blood this will return true if ALL the blood requested was drained.
	 * If the amount drained is greater than the amount available this will return false, and no blood will be drained from the pool
	 */
	@Deprecated
	public boolean addVampireBlood(int amount);

	/**
	 * Gets the amount of blood in a vampire's pool
	 *
	 * @return The amount of blood available
	 */
	public int getBlood();

	/**
	 * Gets the maximum amount of blood that can be stored in a vampire's pool
	 *
	 * @return The amount of blood that can be stored
	 */
	public int getMaxBlood();

	/**
	 * Internal use only. Use TransformationHelper.setVampireBlood instead to keep it syncronized
	 * Sets the amount of blood in a vampire's pool
	 */
	@Deprecated
	public void setBlood(int blood);

	public boolean isNightVisionActive();

	public void setNightVision(boolean flag);
}
