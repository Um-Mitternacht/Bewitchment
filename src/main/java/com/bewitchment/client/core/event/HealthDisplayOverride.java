package com.bewitchment.client.core.event;

import java.util.ArrayList;

import com.bewitchment.common.core.handler.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HealthDisplayOverride {
	
	private static final HealthDisplayOverride INSTANCE = new HealthDisplayOverride();
	
	private boolean override = false;
	private boolean mantleInstalled = false;
	
	private ArrayList<AttributeModifier> mods = new ArrayList<>();
	
	private float health = 0;
	
	private HealthDisplayOverride() {
		setOverride(ConfigHandler.CLIENT.overrideHealth);
		mantleInstalled = Loader.isModLoaded("mantle");
	}
	
	private void setOverride(boolean mode) {
		if (!override && mode) {
			MinecraftForge.EVENT_BUS.register(this);
		} else if (override && !mode) {
			MinecraftForge.EVENT_BUS.unregister(this);
		}
		override = mode;
	}
	
	public static void set(boolean override) {
		INSTANCE.setOverride(override);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onDrawHealthPre(RenderGameOverlayEvent.Pre evt) {
		if (evt.getType() == ElementType.HEALTH && override) {
			if (mantleInstalled) {
				evt.setCanceled(true);
			}
			
			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().getRenderViewEntity();
			health = player.getHealth();
			Minecraft.getMinecraft().fontRenderer.drawString(player.getHealth() + "", 10, 10, 0xFFFFFF);
			GlStateManager.color(1, 1, 1);
			Minecraft.getMinecraft().renderEngine.bindTexture(Gui.ICONS);
			int extraRowsTotal = (int) (health / 20);
			int totalHalfHeartsOnBar = (int) (health % 20);
			if (health > 0 && totalHalfHeartsOnBar == 0) {
				totalHalfHeartsOnBar = 1;
				extraRowsTotal--;
			}
			if (extraRowsTotal > 0) {
				player.setHealth(totalHalfHeartsOnBar);
				mods.addAll(player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifiers());
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeAllModifiers();
				int left = evt.getResolution().getScaledWidth() / 2 - 91;
				int x = left - 20;
				int y = evt.getResolution().getScaledHeight() - 39;
				Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y, 16, 0, 9, 9);
				Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y, 52, 0, 9, 9);
				
				Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y - 9, 16, 0, 9, 9);
				
				String text = TextFormatting.BOLD + "+" + (extraRowsTotal);
				GlStateManager.pushMatrix();
				GlStateManager.scale(0.5, 0.5, 0.5);
				Minecraft.getMinecraft().fontRenderer.drawString(text, 2 * (x + Minecraft.getMinecraft().fontRenderer.getStringWidth(text) - 4), 2 * y + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF, true);
				GlStateManager.popMatrix();
			}
			GlStateManager.color(1, 1, 1);
			Minecraft.getMinecraft().renderEngine.bindTexture(Gui.ICONS);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onDrawHealthPreLast(RenderGameOverlayEvent.Pre evt) {
		if (evt.getType() == ElementType.HEALTH && override && mantleInstalled) {
			evt.setCanceled(false);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onDrawHealthPost(RenderGameOverlayEvent.Post evt) {
		if (evt.getType() == ElementType.HEALTH && override) {
			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().getRenderViewEntity();
			player.setHealth(health);
			for (AttributeModifier am : mods) {
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(am);
			}
			mods.clear();
		}
	}
}
