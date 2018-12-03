package com.bewitchment.client.gui;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.container.ContainerDistillery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;

public class GuiDistillery extends GuiContainer {

	private final ContainerDistillery containerDistillery;

	public GuiDistillery(ContainerDistillery container) {
		super(container);
		containerDistillery = container;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(ResourceLocations.DISTILLERY_GUI);
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		if (containerDistillery.totalTime >= 0) {
			int l = this.getCookProgress(24);
			this.drawTexturedModalRect(i + 84, j + 25, 176, 0, l + 1, 17);
		}
	}

	public int getCookProgress(int pixels) {
		return containerDistillery.progress * pixels / containerDistillery.totalTime;
	}
}