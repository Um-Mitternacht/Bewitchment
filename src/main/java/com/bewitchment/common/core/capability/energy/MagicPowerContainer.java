package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.mp.DefaultMPStorage;
import com.bewitchment.api.mp.IMagicPowerContainer;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class MagicPowerContainer {
	public static void init() {
		CapabilityManager.INSTANCE.register(IMagicPowerContainer.class, new MagicPowerContainerStorage(), () -> new DefaultMPStorage(0));
	}
}
