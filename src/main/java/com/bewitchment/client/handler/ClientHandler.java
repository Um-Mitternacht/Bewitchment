package com.bewitchment.client.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.item.ItemTarotCards;
import com.bewitchment.common.item.ItemWaystone;
import com.bewitchment.common.item.equipment.baubles.ItemGrimoireMagia;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ClientHandler {
	@SubscribeEvent
	public void onTooltipAdd(ItemTooltipEvent event) {
		Item item = event.getItemStack().getItem();
		if (item.getRegistryName().getNamespace().equals(Bewitchment.MODID) && !(item instanceof ItemTarotCards) && !(item instanceof ItemGrimoireMagia) && !(item instanceof ItemWaystone)) {
			String tip = "tooltip." + item.getTranslationKey().substring(5);
			if (!I18n.format(tip).equals(tip)) event.getToolTip().add(1, I18n.format(tip));
		}
	}
}