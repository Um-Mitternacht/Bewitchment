package com.bewitchment.client.gui;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.container.ContainerDistillery;
import com.bewitchment.common.tile.tiles.TileEntityDistillery;
import com.bewitchment.common.tile.tiles.TileEntityOven;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Joseph on 7/17/2017.
 */
public class GuiDistillery extends GuiContainer {

	private final InventoryPlayer playerInventory;
	private final TileEntityDistillery tileOven;
	private final ContainerDistillery containerOven;

	public GuiDistillery(ContainerDistillery container, InventoryPlayer inventory) {
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
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(ResourceLocations.DISTILLERY_GUI);
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

//		int l = this.getCookProgress(24);
//		this.drawTexturedModalRect(i + 76, j + 19, 176, 14, l + 1, 16);
	}

	public int getCookProgress(int pixels) {
		return containerOven.progress * pixels / TileEntityOven.TOTAL_WORK;
	}
}