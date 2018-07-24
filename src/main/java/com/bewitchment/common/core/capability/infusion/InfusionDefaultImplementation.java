package com.bewitchment.common.core.capability.infusion;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.api.infusion.IInfusionCapability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class InfusionDefaultImplementation implements IInfusionCapability {
	
	IInfusion current = DefaultInfusions.NONE;
	
	@Override
	public IInfusion getType() {
		return current;
	}
	
	@Override
	public void setType(IInfusion infusion) {
		current = infusion;
	}
	
	public static void init() {
		CapabilityManager.INSTANCE.register(IInfusionCapability.class, new InfusionStorage(), InfusionDefaultImplementation::new);
	}
	
}
