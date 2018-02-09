package com.bewitchment.client.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExtraBarButtonsHUD {

	// TODO reset these when the user closes the game, either MP or SP
	// TODO sync to the server when the slot changes, keep data inside some capability on the player. Needed to block
	// the item actually selected and trigger the correct power
	int slotSelected = -1;
	boolean isInExtraBar = false;
	int selectedItemTemp = 0;

	public ExtraBarButtonsHUD() {
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void scrollWheelHijacker(MouseEvent evt) {
		int dir = evt.getDwheel() == 0 ? 0 : evt.getDwheel() > 0 ? -1 : 1;
		if (dir == 0)
			return;
		int curItm = Minecraft.getMinecraft().player.inventory.currentItem;
		int max = getMaxActions();
		if (Minecraft.getMinecraft().currentScreen == null) {// Don't mess with scroll wheels if a gui is open, only when playing
			evt.setCanceled(isInExtraBar || (dir < 0 && slotSelected == 0) || (dir > 0 && curItm == 8 && max > 0));
			if (curItm == 8) {
				if (dir > 0) {
					if (curItm == 8 && max > 0) {
						isInExtraBar = true;
						slotSelected++;
						if (slotSelected >= max) {
							slotSelected = max - 1;
						}
					}
				} else if (dir < 0) {
					slotSelected--;
					if (slotSelected < 0) {
						isInExtraBar = false;
					}
				}
			}
			if (slotSelected < -1)
				slotSelected = -1;
		}
	}

	public int getMaxActions() {
		return 5;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void keybordSelectorCheck(KeyInputEvent evt) { // Used to keep it coherent when the player uses the keys 1-9 to pick the selected item
		if (Minecraft.getMinecraft().player.inventory.currentItem < 8) {
			slotSelected = -1;
			isInExtraBar = false;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && isInExtraBar) {

			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

			int i = sr.getScaledWidth() / 2;

			Minecraft.getMinecraft().fontRenderer.drawString("" + slotSelected, i - 92 + 228, sr.getScaledHeight() - 15, 0xffffff, true);
			Minecraft.getMinecraft().player.inventory.currentItem = selectedItemTemp;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && isInExtraBar) {
			selectedItemTemp = Minecraft.getMinecraft().player.inventory.currentItem;
			Minecraft.getMinecraft().player.inventory.currentItem = 11;// Render overlay to the right (increase to something like 100 to make it disappear, if we decide to use a custom selection indicator)
		}
	}

}
