package com.bewitchment.client.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.item.ItemTaglock;
import com.bewitchment.common.item.ItemTarotCards;
import com.bewitchment.common.item.ItemWaystone;
import com.bewitchment.common.item.equipment.baubles.ItemGrimoireMagia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ClientHandler {
	public static int ticksInGame = 0;
	
	@SubscribeEvent
	public void onTooltipAdd(ItemTooltipEvent event) {
		Item item = event.getItemStack().getItem();
		if (item.getRegistryName().getNamespace().equals(Bewitchment.MODID) && !(item instanceof ItemTarotCards) && !(item instanceof ItemGrimoireMagia) && !(item instanceof ItemTaglock) && !(item instanceof ItemWaystone)) {
			String tip = "tooltip." + item.getTranslationKey().substring(5);
			if (!I18n.format(tip).equals(tip)) event.getToolTip().add(1, I18n.format(tip));
		}
		if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("bewitchment_brew")) {
			for (PotionEffect effect : PotionUtils.getEffectsFromStack(event.getItemStack())) {
				if (!effect.doesShowParticles()) {
					event.getToolTip().add(1, I18n.format("bewitchment.modifier.suppressed_particles"));
					break;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void clientTickEnd(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if (gui == null || !gui.doesGuiPauseGame()) ++ticksInGame;
		}
	}
}