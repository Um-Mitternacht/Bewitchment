package com.bewitchment.common.core.capability.mimic;

import java.util.UUID;

public interface IMimicData {
	boolean isMimicking();

	void setMimicking(boolean mimicking);

	UUID getMimickedPlayerID();

	void setMimickedPlayerID(UUID mimickedPlayerID);

	String getMimickedPlayerName();

	void setMimickedPlayerName(String mimickedPlayerName);

	void copyFields(IMimicData data);
}
