package com.bewitchment.common.content.infusion.capability;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.api.infusion.IInfusionCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class InfusionCapability implements IInfusionCapability {

	@CapabilityInject(IInfusionCapability.class)
	public static final Capability<IInfusionCapability> CAPABILITY = null;

	IInfusion current = DefaultInfusions.NONE;

	public static void init() {
		CapabilityManager.INSTANCE.register(IInfusionCapability.class, new InfusionStorage(), InfusionCapability::new);
	}

	@Override
	public IInfusion getType() {
		return current == null ? DefaultInfusions.NONE : current;
	}

	@Override
	public void setType(IInfusion infusion) {
		current = infusion;
	}

}
