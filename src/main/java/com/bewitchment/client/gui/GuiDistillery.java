package com.bewitchment.client.gui;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.container.ContainerDistillery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDistillery extends GuiContainer {
    private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/gui/distillery.png");

    private final ContainerDistillery container;

    public GuiDistillery(ContainerDistillery container) {
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
        drawTexturedModalRect(x + 76, y + 16, 176, 0, container.progress * 24 / 200 + 1, 17);
        int burnProgress = 14 - (int) Math.ceil(14 * (container.burnTime / (double) 1200));
        drawTexturedModalRect(x + 81, y + 36 + burnProgress, 242, burnProgress, 14, 14 - burnProgress);
    }
}