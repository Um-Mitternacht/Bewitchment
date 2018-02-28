package com.bewitchment.common.core.capability.transformation;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityTransformationData implements ITransformationData {

	@CapabilityInject(ITransformationData.class)
	public static final Capability<ITransformationData> CAPABILITY = null;

	EnumTransformationType type;
	int level, blood;
	boolean isNightVisionActive;

	public CapabilityTransformationData() {
		type = EnumTransformationType.NONE;
		level = 0;
		blood = 0;
		isNightVisionActive = false;
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(ITransformationData.class, new TransformationDataStorage(), CapabilityTransformationData::new);
	}

	@Override
	public EnumTransformationType getType() {
		return type;
	}

	/**
	 * Prefer the use of {@link com.bewitchment.common.core.helper.TransformationHelper#setTypeAndLevel()} over this. That one automatically takes
	 * care of syncronization and refreshing data
	 */
	@Override
	public void setType(EnumTransformationType type) {
		this.type = type;
		if (type != EnumTransformationType.VAMPIRE) {
			blood = 0;
		} else {
			blood = getMaxBlood();
		}
	}

	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * Prefer the use of {@link com.bewitchment.common.core.helper.TransformationHelper#setTypeAndLevel()} over this. That one automatically takes
	 * care of syncronization and refreshing data
	 */
	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public boolean addVampireBlood(int amount) {
		if (getType() != EnumTransformationType.VAMPIRE)
			throw new UnsupportedOperationException("Player is not a vampire, cannot add blood");
		if (getBlood() >= getMaxBlood() && amount > 0)
			return false;
		if (amount + getBlood() < 0)
			return false;
		blood += amount;
		return true;
	}

	@Override
	public int getBlood() {
		if (getType() != EnumTransformationType.VAMPIRE)
			throw new UnsupportedOperationException("Player is not a vampire, cannot add blood");
		return blood;
	}

	@Override
	public int getMaxBlood() {
		if (getType() != EnumTransformationType.VAMPIRE)
			throw new UnsupportedOperationException("Player is not a vampire, cannot add blood");
		int max = 50 + 75 * getLevel();
		if (getBlood() > max) {
			setBlood(max);
		}
		return max; // lvl 0: 50, lvl 5: 425, lvl 10: 800
	}

	@Override
	public void setBlood(int blood) {
		if (getType() != EnumTransformationType.VAMPIRE)
			throw new UnsupportedOperationException("Player is not a vampire, cannot add blood");
		this.blood = blood;
	}

	@Override
	public boolean isNightVisionActive() {
		return isNightVisionActive;
	}

	@Override
	public void setNightVision(boolean flag) {
		isNightVisionActive = flag;
	}

}
