package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.mp.IMagicPowerUsingItem;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class MagicPowerUsingItem {
	private static final IMagicPowerUsingItem INSTANCE = new IMagicPowerUsingItem() {
	};

	public static void init() {
		CapabilityManager.INSTANCE.register(IMagicPowerUsingItem.class, new IMagicPowerUsingItem.Storage(), () -> INSTANCE);
	}
}
