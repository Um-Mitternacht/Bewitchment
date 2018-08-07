package com.bewitchment.common.core.event;

import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MimicEvents {

	@SubscribeEvent
	public static void onChatMessage(ServerChatEvent event) {
		if (event.getPlayer().hasCapability(CapabilityMimicData.CAPABILITY, null)) {
			final IMimicData capability = event.getPlayer().getCapability(CapabilityMimicData.CAPABILITY, null);
			if (capability.isMimicking()) {
				event.setCanceled(true);
				event.getPlayer().getServerWorld().playerEntities.stream().forEach(player ->
						player.sendMessage(new TextComponentString("<" + capability.getMimickedPlayerName() + "> " + event.getMessage())));
			}
		}
	}

	@SubscribeEvent
	public static void onDisplayName(PlayerEvent.NameFormat event) {
		if (event.getEntity().hasCapability(CapabilityMimicData.CAPABILITY, null)) {
			final IMimicData capability = event.getEntity().getCapability(CapabilityMimicData.CAPABILITY, null);
			if (capability.isMimicking()) {
				event.setDisplayname(capability.getMimickedPlayerName());
			}
		}
	}
}
