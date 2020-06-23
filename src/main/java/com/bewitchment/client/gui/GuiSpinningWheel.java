package com.bewitchment.client.gui;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.container.ContainerSpinningWheel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSpinningWheel extends GuiContainer {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/gui/spinning_wheel.png");
	
	private final ContainerSpinningWheel container;
	
	public GuiSpinningWheel(ContainerSpinningWheel container) {
		super(container);
		this.container = container;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEX);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 85, y + 33, 176, 0, container.progress * 24 / 200, 17);
	}
}