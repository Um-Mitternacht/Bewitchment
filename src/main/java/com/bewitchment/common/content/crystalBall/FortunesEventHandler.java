package com.bewitchment.common.content.crystalBall;

import com.bewitchment.api.divination.IFortune;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import com.bewitchment.common.core.helper.CapabilityHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber
public class FortunesEventHandler {

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.Clone event) {
		CapabilityHelper.copyDataOnPlayerRespawn(event, CapabilityFortune.CAPABILITY);
	}

	@SubscribeEvent
	public static void onLivingTick(PlayerTickEvent evt) {
		if (!evt.player.world.isRemote && evt.phase == Phase.END) {
			CapabilityFortune data = evt.player.getCapability(CapabilityFortune.CAPABILITY, null);
			IFortune f = data.getFortune();
			if (f != null) {
				if (data.isRemovable()) {
					data.setFortune(null);
				} else {
					if (f.canShouldBeAppliedNow(evt.player)) {
						data.setActive();
					}

					if (data.isActive()) {
						if (f.apply(evt.player))
							data.setFortune(null);
					}
				}
			}
		}
	}
}
