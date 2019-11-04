package com.bewitchment.common.handler;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.event.CurseEvent;
import com.bewitchment.registry.ModCurses;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CurseHandler {
	@SubscribeEvent
	public void onPlayerCursed(CurseEvent.PlayerCursedEvent event) {
		if (event.getTarget().hasCapability(ExtendedPlayer.CAPABILITY, null) && event.getCaster().hasCapability(ExtendedPlayer.CAPABILITY, null)) {
			ExtendedPlayer targetCap = event.getTarget().getCapability(ExtendedPlayer.CAPABILITY, null);
			ExtendedPlayer casterCap = event.getCaster().getCapability(ExtendedPlayer.CAPABILITY, null);
			if (targetCap.hasCurse(ModCurses.curseReturnToSender) && event.getCurse().isLesser()) {
				event.setTarget(event.getCaster());
				targetCap.removeCurse(ModCurses.curseReturnToSender);
				casterCap.removeCurse(ModCurses.curseReturnToSender);
			}
		}
	}
}
