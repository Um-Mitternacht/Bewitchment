package com.bewitchment.client.gui;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.client.gui.container.ContainerApiary;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class GuiApiary extends GuiContainer {

	private final InventoryPlayer playerInventory;
	private final IInventory tileApiary;

	public GuiApiary(InventoryPlayer playerInventory, IInventory tileApiary) {
		super(new ContainerApiary(playerInventory, tileApiary));
		this.playerInventory = playerInventory;
		this.tileApiary = tileApiary;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		final String s = this.tileApiary.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(ResourceLocations.APIARY_GUI);
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
}
