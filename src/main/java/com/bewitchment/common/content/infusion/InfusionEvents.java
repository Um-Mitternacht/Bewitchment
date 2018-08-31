package com.bewitchment.common.content.infusion;

import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.InfusionChangedMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber
public class InfusionEvents {

	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent evt) {
		if (evt.player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new InfusionChangedMessage(evt.player), (EntityPlayerMP) evt.player);
		}
	}

}
