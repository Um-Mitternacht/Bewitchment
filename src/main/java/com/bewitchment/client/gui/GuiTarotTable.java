package com.bewitchment.client.gui;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Tarot;
import com.bewitchment.common.block.tile.container.ContainerTarotTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiTarotTable extends GuiContainer {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot_table.png");
	
	private final ContainerTarotTable container;
	
	public GuiTarotTable(ContainerTarotTable container) {
		super(container);
		this.container = container;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEX);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		for (int i = 0; i < container.toRead.size(); i++) {
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1);
			Tarot tarot = container.toRead.get(i);
			if (tarot.isReversed(container.player)) GlStateManager.scale(1, -1, 1);
			mc.getTextureManager().bindTexture(tarot.texture);
			int cx = x + (i % 2 == 0 ? 24 : 102);
			int cy = y + (i / 2 == 0 ? 12 : 90);
			drawTexturedModalRect(cx, cy, 0, 0, 48, 64);
			GlStateManager.popMatrix();
			if (tarot.getNumber(container.player) > -1) {
				int num = tarot.getNumber(container.player);
				String number = Math.min(99, num) + (num > 99 ? "+" : "");
				drawCenteredString(mc.fontRenderer, number, cx + 24, cy + 54, 0x7f7f7f);
			}
		}
	}
}