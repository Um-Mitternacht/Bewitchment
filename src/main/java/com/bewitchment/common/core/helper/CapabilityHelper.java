package com.bewitchment.common.core.helper;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CapabilityHelper {

	public static <T> void copyDataOnPlayerRespawn(PlayerEvent.Clone event, Capability<T> c) {
		c.getStorage().readNBT(c, event.getEntityPlayer().getCapability(c, null), null, c.writeNBT(event.getOriginal().getCapability(c, null), null));
	}

}
