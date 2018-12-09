package com.bewitchment.client.gui;

import com.bewitchment.common.content.tarot.TarotHandler.TarotInfo;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class GuiTarots extends GuiScreen {

	protected static final ResourceLocation background = new ResourceLocation(LibMod.MOD_ID, "textures/gui/tarot_gui.png");
	protected static final ResourceLocation card_frame = new ResourceLocation(LibMod.MOD_ID, "textures/gui/tarot_frame.png");
	protected static final ResourceLocation card_frame_number = new ResourceLocation(LibMod.MOD_ID, "textures/gui/tarot_frame_number.png");

	ArrayList<TarotButton> buttons = new ArrayList<TarotButton>(0); // buttonList acts funky, I add a button but when drawScreen gets called the list is empty
	ArrayList<TarotInfo> data = new ArrayList<TarotInfo>(0);
	int pressed = -1;
	boolean dataReceived = false;

	public GuiTarots() {

	}

	public void loadData(ArrayList<TarotInfo> fromNetwork) {
		this.data = fromNetwork;
		this.buttons = new ArrayList<TarotButton>(data.size());
		this.setGuiSize(252, 192);
		int t = data.size();
		for (int i = 0; i < t; i++) {
			int qx = ((252 - (22 * t)) / (t + 1)) * (i + 1) + (i * 22);
			TarotButton tb = new TarotButton(i, qx, 160);
			this.buttons.add(tb);
			if (i == 0) {
				tb.setPressed(true);
				pressed = 0;
			}
		}
		dataReceived = true;
		ScaledResolution sr = new ScaledResolution(mc);
		this.onResize(mc, sr.getScaledWidth(), sr.getScaledHeight());
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
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
		if (dataReceived) {
			drawCard();
			GL11.glColor4f(1f, 1f, 1f, 1f);
			for (int i = 0; i < this.buttons.size(); ++i) {
				this.buttons.get(i).drawButton(this.mc, mouseX, mouseY, partialTicks);
			}

			for (int i = 0; i < this.buttons.size(); ++i) {
				if (this.buttons.get(i).isMouseOver()) {
					drawHoveringText(TextFormatting.LIGHT_PURPLE + I18n.format(data.get(i).getTranslationKey()), mouseX, mouseY);
				}
			}
		} else {
			String reading = I18n.format("tarots.reading");
			ScaledResolution sr = new ScaledResolution(mc);
			int x = ((sr.getScaledWidth() - mc.fontRenderer.getStringWidth(reading)) / 2);
			int y = ((sr.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT) / 2);
			mc.fontRenderer.drawString(reading, x, y, 0xFCD71C, false);
		}

	}

	private void drawCard() {
		if (pressed < 0)
			return; // no card selected
		double scale = 0.5d;
		TarotInfo t = data.get(pressed);
		ScaledResolution sr = new ScaledResolution(mc);
		int left = ((sr.getScaledWidth() - 252) / 2);
		int top = ((sr.getScaledHeight() - 192) / 2);
		int cardX = (int) (left + ((252 - 192 * scale) / 2));
		int cardY = (int) (top + 15 + ((146 - 256 * scale) / 2));
		GL11.glPushMatrix();
		if (t.isReversed()) {
			GlStateManager.translate(cardX + (192 * scale / 2), cardY + (256 * scale / 2), 0); // Center on the card center
			GlStateManager.rotate(180, 0, 0, 1);// Rotate everything
			GlStateManager.translate(-cardX - (192 * scale / 2), -cardY - (256 * scale / 2), 0); // Go back
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(t.getTexture());
		drawModalRectWithCustomSizedTexture(cardX, cardY, 0f, 0f, (int) (192 * scale), (int) (256 * scale), (int) (192 * scale), (int) (256 * scale));
		Minecraft.getMinecraft().renderEngine.bindTexture(t.hasNumber() ? card_frame_number : card_frame);
		drawModalRectWithCustomSizedTexture((int) (left + ((252 - 192 * scale) / 2)), (int) (top + 15 + ((146 - 256 * scale) / 2)), 0f, 0f, (int) (192 * scale), (int) (256 * scale), (int) (192 * scale), (int) (256 * scale));

		if (t.hasNumber()) {
			String num = "" + t.getNumber();
			mc.fontRenderer.drawString(num, left + ((252 - mc.fontRenderer.getStringWidth(num)) / 2), top + 139, 0xFCD71C, true);
		}
		GL11.glPopMatrix();
		String name = I18n.format(t.getTranslationKey());
		mc.fontRenderer.drawString(name, left + ((252 - mc.fontRenderer.getStringWidth(name)) / 2), top + 14, 0xFCD71C, true);


	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		buttons.forEach(b -> b.setPressed(b == button));
		pressed = button.id;
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
