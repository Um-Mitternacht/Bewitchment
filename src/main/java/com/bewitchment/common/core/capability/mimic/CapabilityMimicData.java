package com.bewitchment.common.core.capability.mimic;

import com.bewitchment.common.Bewitchment;
import net.minecraft.entity.player.EntityPlayer;
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

	@Override
	public boolean isMimicking() {
		return mimicking;
	}

	@Override
	public void setMimicking(boolean mimickingIn, EntityPlayer p) {
		setMimickingDirect(mimickingIn);
		if (!mimickingIn) {
			Bewitchment.proxy.stopMimicking(p);
		}
	}

	@Override
	public UUID getMimickedPlayerID() {
		return mimickedPlayerID;
	}

	@Override
	public void setMimickedPlayerID(UUID mimickedPlayerID) {
		this.mimickedPlayerID = mimickedPlayerID;
	}

	@Override
	public String getMimickedPlayerName() {
		return mimickedPlayerName;
	}

	@Override
	public void setMimickedPlayerName(String mimickedPlayerName) {
		this.mimickedPlayerName = mimickedPlayerName;
	}

	/**
	 * Calling this won't call cleanup methods, and should only be used when restoring data from NBT
	 * Prefer the use of {@link IMimicData#setMimicking(boolean, EntityPlayer)}
	 */
	@Override
	public void setMimickingDirect(boolean mimickingIn) {
		this.mimicking = mimickingIn;
	}
}
