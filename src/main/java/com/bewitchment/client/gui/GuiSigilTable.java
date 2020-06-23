package com.bewitchment.client.gui;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.container.ContainerSigilTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiSigilTable extends GuiContainer {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/gui/sigil_table.png");
	private final ContainerSigilTable container;
	
	public GuiSigilTable(ContainerSigilTable container) {
		super(container);
		this.container = container;
		ySize = 192;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
		mc.getTextureManager().bindTexture(TEX);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
