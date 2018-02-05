package com.bewitchment.common.core.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CapabilityUtils {
	
	public static <T> void copyDataOnPlayerRespawn(PlayerEvent.Clone event, Capability<T> c) {
		c.getStorage().readNBT(c, event.getEntityPlayer().getCapability(c, null), null, c.writeNBT(event.getOriginal().getCapability(c, null), null));
	}
	
}
