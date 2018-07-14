package com.bewitchment.common.core.capability.mimic;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.UUID;

public class CapabilityMimicData implements IMimicData {

	@CapabilityInject(IMimicData.class)
	public static final Capability<IMimicData> CAPABILITY = null;

	private boolean mimicking;
	private UUID mimickedPlayerID;
	private String mimickedPlayerName;

	public CapabilityMimicData() {
		mimicking = false;
		mimickedPlayerID = new UUID(0, 0);
		mimickedPlayerName = "";
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(IMimicData.class, new MimicDataStorage(), CapabilityMimicData::new);
	}

	public boolean isMimicking() {
		return mimicking;
	}

	public void setMimicking(boolean mimicking) {
		this.mimicking = mimicking;
	}

	public UUID getMimickedPlayerID() {
		return mimickedPlayerID;
	}

	public void setMimickedPlayerID(UUID mimickedPlayerID) {
		this.mimickedPlayerID = mimickedPlayerID;
	}

	public String getMimickedPlayerName() {
		return mimickedPlayerName;
	}

	public void setMimickedPlayerName(String mimickedPlayerName) {
		this.mimickedPlayerName = mimickedPlayerName;
	}

	@Override
	public void copyFields(IMimicData data) {
		this.setMimicking(data.isMimicking());
		this.setMimickedPlayerID(data.getMimickedPlayerID());
		this.setMimickedPlayerName(data.getMimickedPlayerName());
	}
}
