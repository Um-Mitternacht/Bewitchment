package com.bewitchment.client.gui;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.container.ContainerOven;
import com.bewitchment.common.tile.tiles.TileEntityOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Joseph on 7/17/2017.
 */
public class GuiOven extends GuiContainer {

	private final InventoryPlayer playerInventory;
	private final TileEntityOven tileOven;
	private final ContainerOven containerOven;

	public GuiOven(ContainerOven container, InventoryPlayer inventory) {
		super(container);
		containerOven = container;
		this.playerInventory = inventory;
		this.tileOven = container.getTileEntity();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = tileOven.getName();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(ResourceLocations.OVEN_GUI);
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (containerOven.gui_data[3] > 0) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(i + 44, j + 50 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.getCookProgress(24);
		this.drawTexturedModalRect(i + 76, j + 19, 176, 14, l + 1, 16);
	}

	public int getCookProgress(int pixels) {
		return containerOven.gui_data[2] * pixels / TileEntityOven.TOTAL_WORK;
	}

	private int getBurnLeftScaled(int pixels) {
		if (containerOven.gui_data[0] > 0) {
			return (containerOven.gui_data[1] - containerOven.gui_data[0]) * pixels / containerOven.gui_data[1];
		}
		return 0;
	}
}