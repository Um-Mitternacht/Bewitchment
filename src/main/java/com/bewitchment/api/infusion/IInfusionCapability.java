package com.bewitchment.api.infusion;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IInfusionCapability {
	@CapabilityInject(IInfusionCapability.class)
	public static final Capability<IInfusionCapability> CAPABILITY = null;

	public IInfusion getType();

	public void setType(IInfusion infusion);
}
