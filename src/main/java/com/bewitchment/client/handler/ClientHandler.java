package com.bewitchment.client.handler;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientHandler {
	@SubscribeEvent
	public void onTooltipAdd(ItemTooltipEvent event) {
		Item item = event.getItemStack().getItem();
		String tip = "tooltip." + item.getTranslationKey().substring(5);
		if (!I18n.format(tip).equals(tip)) event.getToolTip().add(1, I18n.format(tip));
	}
}