package com.bewitchment.client.gui;


import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.container.ContainerThreadSpinner;
import com.bewitchment.common.tile.tiles.TileEntityThreadSpinner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiThreadSpinner extends GuiContainer {
	TileEntityThreadSpinner tileEntity;

	public GuiThreadSpinner(InventoryPlayer playerInventory, TileEntityThreadSpinner te) {
		super(new ContainerThreadSpinner(playerInventory, te));
		this.xSize = 176;
		this.ySize = 166;
		this.tileEntity = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawDefaultBackground();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceLocations.THREAD_SPINNER_GUI);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		double progress = (double) tileEntity.getWork() / (TileEntityThreadSpinner.TOTAL_WORK - 10);
		drawTexturedModalRect(guiLeft + 85, guiTop + 33, 176, 0, (int) (22 * progress), 17);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String s = tileEntity.getName();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.renderHoveredToolTip(mouseX - guiLeft, mouseY - guiTop);
	}

}
