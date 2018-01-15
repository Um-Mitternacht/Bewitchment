package com.bewitchment.common.core.capability.divination;

import com.bewitchment.api.divination.Fortune;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public interface CapabilityDivination {

	@CapabilityInject(CapabilityDivination.class)
	public static final Capability<CapabilityDivination> CAPABILITY = null;

	public static void init() {
		CapabilityManager.INSTANCE.register(CapabilityDivination.class, new DivinationStorage(), Impl::new);
	}

	public void setFortune(@Nullable Fortune fortune);

	@Nullable
	public Fortune getFortune();

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
}
