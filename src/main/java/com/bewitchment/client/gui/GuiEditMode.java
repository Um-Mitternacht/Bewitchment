package com.bewitchment.client.gui;

import com.bewitchment.client.core.hud.HudController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public class GuiEditMode extends GuiScreen {

	public GuiEditMode() {
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawGradientRect(0, 0, this.width, this.height, 0xdd555555, 0xdd555555);
		HudController.INSTANCE.render(new ScaledResolution(Minecraft.getMinecraft()), partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton != -1) {
			HudController.INSTANCE.onMouseInteraction(mouseX, mouseY);
		}
	}

	@Override
	public void onGuiClosed() {
		HudController.INSTANCE.ungrab();
	}

}
