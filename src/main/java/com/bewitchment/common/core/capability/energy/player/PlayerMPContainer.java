package com.bewitchment.common.core.capability.energy.player;

import com.bewitchment.api.mp.DefaultMPContainer;

public class PlayerMPContainer extends DefaultMPContainer {

	boolean dirty = false;

	public PlayerMPContainer() {
		super(800);
	}

	@Override
	public void setAmount(int newAmount) {
		dirty = true;
		super.setAmount(newAmount);
	}

	@Override
	public void setMaxAmount(int newMaxAmount) {
		dirty = true;
		super.setMaxAmount(newMaxAmount);
	}

	public void setClean() {
		dirty = false;
	}

	public boolean isDirty() {
		return dirty;
	}
}
