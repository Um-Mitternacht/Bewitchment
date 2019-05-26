package com.bewitchment.client.handler;

import com.bewitchment.common.item.equipment.baubles.ItemGrimoireMagia;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class ClientHandler {
	@SubscribeEvent
	public void onTooltipAdd(ItemTooltipEvent event) {
		Item item = event.getItemStack().getItem();
		if (!(item instanceof ItemGrimoireMagia)) {
			String tip = "tooltip." + item.getTranslationKey().substring(5);
			if (!I18n.format(tip).equals(tip)) event.getToolTip().add(1, I18n.format(tip));
		}
	}
}