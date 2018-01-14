package com.bewitchment.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IItemEnergyUser {

	@CapabilityInject(IItemEnergyUser.class)
	public static final Capability<IItemEnergyUser> ENERGY_USER_CAPABILITY = null;

	boolean shouldShowHud();
}
