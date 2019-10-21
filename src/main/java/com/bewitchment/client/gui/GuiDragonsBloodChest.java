package com.bewitchment.client.gui;

import com.bewitchment.common.block.tile.container.ContainerDBChest;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDragonsBloodChest extends GuiContainer {
	private static final ResourceLocation TEX = new ResourceLocation("textures/gui/container/shulker_box.png");
	
	private final InventoryPlayer inventory;
	
	public GuiDragonsBloodChest(ContainerDBChest container, InventoryPlayer inventory) {
		super(container);
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
		this.fontRenderer.drawString(I18n.format(ModObjects.dragons_blood_chest.getTranslationKey() + ".name"), 8, 6, 0x404040);
		fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 7, ySize - 93, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEX);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
