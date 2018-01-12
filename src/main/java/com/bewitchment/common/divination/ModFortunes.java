package com.bewitchment.common.divination;


import com.bewitchment.api.divination.Fortune;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import com.bewitchment.common.lib.LibMod;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ModFortunes {
	
	private static Fortune zombie;
	
	public static void init() {
		zombie = new FortuneMeetZombie(10, "meet_zombie", LibMod.MOD_ID);
	}
	
	public void onRegisterFortunes(RegistryEvent.Register<Fortune> evt) {
		evt.getRegistry().registerAll(
				zombie
		);
	}
	
	@SubscribeEvent
	public void onLivingTick(PlayerTickEvent evt) {
		if (!evt.player.world.isRemote && evt.phase==Phase.END) {
			CapabilityDivination data = evt.player.getCapability(CapabilityDivination.CAPABILITY, null);
			Fortune f = data.getFortune();
			if (f!=null) {
				if (f.canShouldBeAppliedNow(evt.player)) {
					if (f.apply(evt.player)) data.setFortune(null);
				}
			}
		}
	}
	
}
