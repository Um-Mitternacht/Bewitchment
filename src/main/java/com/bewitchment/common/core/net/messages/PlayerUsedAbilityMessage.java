package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.event.HotbarAction;
import com.bewitchment.api.event.HotbarActionCollectionEvent;
import com.bewitchment.api.event.HotbarActionTriggeredEvent;
import com.bewitchment.common.core.net.SimpleMessage;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerUsedAbilityMessage extends SimpleMessage<PlayerUsedAbilityMessage> {

	public String ability;
	
	public PlayerUsedAbilityMessage() {
	}

	public PlayerUsedAbilityMessage(String ability) {
		this.ability = ability;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage handleMessage(MessageContext context) {
		HotbarAction action = HotbarAction.getFromRegistryName(ability);
		HotbarActionCollectionEvent evt = new HotbarActionCollectionEvent(context.getServerHandler().player, context.getServerHandler().player.world);
		MinecraftForge.EVENT_BUS.post(evt);
		if (evt.getList().contains(action)) {
			MinecraftForge.EVENT_BUS.post(new HotbarActionTriggeredEvent(action, context.getServerHandler().player, context.getServerHandler().player.world));
		}
		return null;
	}

}
