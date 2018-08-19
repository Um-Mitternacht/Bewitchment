package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.mp.DefaultMPContainer;
import com.bewitchment.api.mp.IMagicPowerContainer;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class MagicPowerContainer {
	public static void init() {
		CapabilityManager.INSTANCE.register(IMagicPowerContainer.class, new MagicPowerContainerStorage(), () -> new DefaultMPContainer(0));
	}
}
