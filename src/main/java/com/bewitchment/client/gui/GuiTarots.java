package com.bewitchment.client.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTarots extends GuiScreen {
	
	protected static final ResourceLocation background = new ResourceLocation(LibMod.MOD_ID, "textures/gui/tarot_gui.png");
	
	EntityPlayer player;
	ArrayList<TarotButton> buttons = new ArrayList<TarotButton>(4); // buttonList acts funky, I add a button but when drawScreen gets called the list is empty
	
	public GuiTarots(EntityPlayer player) {
		this.player = player;
		this.setGuiSize(252, 192);
		int t=getTarots();
		for (int i = 0; i < t; i++) {
			int qx = ((252 - (22 * t)) / (t + 1)) * (i + 1) + (i * 22);
			TarotButton tb = new TarotButton(i, qx, 160);
			this.buttons.add(tb);
			if (i == 0)
				tb.setPressed(true);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	public int getTarots() {
		return 5;
	}

	@Override
	public void drawDefaultBackground() {
		Minecraft.getMinecraft().renderEngine.bindTexture(background);
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		drawTexturedModalRect((sr.getScaledWidth() - 252) / 2, (sr.getScaledHeight() - 192) / 2, 0, 0, 252, 192);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		for (int i = 0; i < this.buttons.size(); ++i) {
			this.buttons.get(i).drawButton(this.mc, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		buttons.forEach(b -> b.setPressed(b == button));
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0) {
			for (int i = 0; i < this.buttons.size(); ++i) {
				TarotButton guibutton = this.buttons.get(i);
				if (!guibutton.pressed && guibutton.mousePressed(this.mc, mouseX, mouseY)) {
					guibutton.playPressSound(this.mc.getSoundHandler());
					this.actionPerformed(guibutton);
				}
			}
		}
	}
	
	public static class TarotButton extends GuiButton {
		
		private boolean pressed = false;
		
		public TarotButton(int buttonId, int x, int y) {
			super(buttonId, x, y, "");
			this.visible = true;
			this.width = 22;
			this.height = 26;
		}
		
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			ScaledResolution sr = new ScaledResolution(mc);
			int left = ((sr.getScaledWidth() - 252) / 2);
			int top = ((sr.getScaledHeight() - 192) / 2);
			int mxn = mouseX - left;
			int myn = mouseY - top;
			this.hovered = mxn >= this.x && myn >= this.y && mxn < this.x + this.width && myn < this.y + this.height;
			mc.renderEngine.bindTexture(background);
			drawTexturedModalRect(left + this.x, top + this.y - (this.isMouseOver() ? 2 : 0), (pressed || this.isMouseOver()) ? 0 : 22, 192, 22, 26);
			drawTexturedModalRect(left + this.x, top + this.y - (this.isMouseOver() ? 2 : 0), pressed ? 0 : 22, 218, 22, 26);
		}
		
		public void setPressed(boolean p) {
			System.out.println("pressing " + this.id);
			pressed = p;
		}
		
		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			ScaledResolution sr = new ScaledResolution(mc);
			int left = ((sr.getScaledWidth() - 252) / 2);
			int top = ((sr.getScaledHeight() - 192) / 2);
			int mxn = mouseX - left;
			int myn = mouseY - top;
			return mxn >= this.x && myn >= this.y && mxn < this.x + this.width && myn < this.y + this.height;
		}
	}
}
