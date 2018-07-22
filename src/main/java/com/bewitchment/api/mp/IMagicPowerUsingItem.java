package com.bewitchment.api.mp;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IMagicPowerUsingItem {
	/**
	 * This class is only here to make items that have this capability show the MP bar. No additional content required
	 */
	@CapabilityInject(IMagicPowerUsingItem.class)
	public static final Capability<IMagicPowerUsingItem> CAPABILITY = null;
	
}
