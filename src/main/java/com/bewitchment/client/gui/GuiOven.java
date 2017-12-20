package com.bewitchment.client.gui;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.client.gui.container.ContainerOven;
import com.bewitchment.common.tile.TileOven;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Joseph on 7/17/2017.
 */
public class GuiOven extends GuiContainer {

	private final InventoryPlayer playerInventory;
	private final TileOven tileOven;

	public GuiOven(InventoryPlayer playerInventory, TileOven tileOven) {
		super(new ContainerOven(playerInventory, tileOven));
		this.playerInventory = playerInventory;
		this.tileOven = tileOven;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = I18n.format("tile.oven.name");
		if (this.tileOven.inventory.hasCustomName())
			s = this.tileOven.inventory.getName();
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

		if (tileOven.isBurning) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(i + 19, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.getCookProgress(24);
		this.drawTexturedModalRect(i + 68, j + 22, 176, 14, l + 1, 16);
	}

	public int getCookProgress(int pixels) {
		return this.tileOven.workTime * pixels / this.tileOven.totalWorkTime;
	}

	private int getBurnLeftScaled(int pixels) {
		if (tileOven.itemBurnTime > 0) {
			return (this.tileOven.itemBurnTime - this.tileOven.burnTime) * pixels / tileOven.itemBurnTime;
		}
		return 0;
	}
}