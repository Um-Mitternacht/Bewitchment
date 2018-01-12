package com.bewitchment.common.core.capability.divination;

import javax.annotation.Nullable;

import com.bewitchment.api.divination.Fortune;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public interface CapabilityDivination {

	@CapabilityInject(CapabilityDivination.class)
	public static final Capability<CapabilityDivination> CAPABILITY = null;

	public void setFortune(@Nullable Fortune fortune);
	@Nullable public Fortune getFortune();

	public static class Impl implements CapabilityDivination {

		private Fortune fortune = null;

		@Override
		public void setFortune(Fortune fortune) {
			this.fortune = fortune;
		}

		@Override
		public Fortune getFortune() {
			return fortune;
		}
	}
	
	public static void init() {
		CapabilityManager.INSTANCE.register(CapabilityDivination.class, new DivinationStorage(), Impl::new);
	}
}
