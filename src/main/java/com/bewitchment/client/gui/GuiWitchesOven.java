package com.bewitchment.client.gui;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.container.ContainerWitchesOven;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWitchesOven extends GuiContainer {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/gui/witches_oven.png");

	private final InventoryPlayer inventory;
	private final ContainerWitchesOven container;

	public GuiWitchesOven(ContainerWitchesOven container, InventoryPlayer inventory) {
		super(container);
		this.container = container;
		this.inventory = inventory;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = new TextComponentTranslation(ModObjects.witches_oven.getTranslationKey() + ".name").getFormattedText();
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 7, ySize - 93, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEX);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		if (container.burnTime > 0) {
			int time = container.burnTime * 13 / Math.max(1, container.fuelBurnTime);
			this.drawTexturedModalRect(x + 44, y + 50 - time, 176, 12 - time, 14, time + 1);
		}
		this.drawTexturedModalRect(x + 76, y + 19, 176, 14, container.progress * 24 / 200 + 1, 16);
	}
}