package com.bewitchment.client.core.event;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bewitchment.api.event.HotbarAction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExtraBarButtonsHUD {
	
	public static final ExtraBarButtonsHUD INSTANCE = new ExtraBarButtonsHUD();

	// TODO reset these when the user closes the game, either MP or SP
	// TODO sync to the server when the slot changes, keep data inside some capability on the player. Needed to block
	// the item actually selected and trigger the correct power
	int slotSelected = -1;
	boolean isInExtraBar = false;
	int selectedItemTemp = 0;
	List<HotbarAction> actions = new ArrayList<HotbarAction>();
	HotbarAction[] actionScroller = new HotbarAction[3];// 0: current, 1: prev, 2: next

	private ExtraBarButtonsHUD() {
	}

	@SubscribeEvent
	public void scrollWheelHijacker(MouseEvent evt) {
		int dir = evt.getDwheel() == 0 ? 0 : evt.getDwheel() > 0 ? -1 : 1;
		if (dir == 0 || (!Minecraft.getMinecraft().player.isSneaking() && !isInExtraBar))
			return;
		int curItm = Minecraft.getMinecraft().player.inventory.currentItem;
		int max = actions.size();
		if (Minecraft.getMinecraft().currentScreen == null) {// Don't mess with scroll wheels if a gui is open, only when playing
			refreshSelected();
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
			refreshSelected();
		}
	}

	private void refreshSelected() {
		if (slotSelected >= 0) {
			actionScroller[0] = actions.get(slotSelected);
		} else {
			actionScroller = new HotbarAction[3];
			return;
		}
		if (slotSelected > 0) {
			actionScroller[1] = actions.get(slotSelected - 1);
		} else {
			actionScroller[1] = null;
		}
		if (slotSelected + 1 < actions.size()) {
			actionScroller[2] = actions.get(slotSelected + 1);
		} else {
			actionScroller[2] = null;
		}
	}
	
	public void setList(List<HotbarAction> list) {
		this.actions = list;
		this.slotSelected = Math.min(slotSelected, list.size() - 1);
		refreshSelected();
		System.out.println("List reloaded: " + list.size());
	}
	

	@SubscribeEvent
	public void keybordSelectorCheck(KeyInputEvent evt) { // Used to keep it coherent when the player uses the keys 1-9 to pick the selected item
		if (Minecraft.getMinecraft().player.inventory.currentItem < 8) {
			slotSelected = -1;
			isInExtraBar = false;
		}
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && isInExtraBar) {
			Minecraft.getMinecraft().player.inventory.currentItem = selectedItemTemp;
		}
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && isInExtraBar) {
			selectedItemTemp = Minecraft.getMinecraft().player.inventory.currentItem;
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			if (actionScroller[0] != null) {
				mc.getTextureManager().bindTexture(actionScroller[0].getIcon(mc.player));
				renderTextureAtIndex((sr.getScaledWidth() / 2) + 131.5, sr.getScaledHeight() - 19.5, actionScroller[0].getIconIndexX(mc.player), actionScroller[0].getIconIndexY(mc.player));
			}
			mc.player.inventory.currentItem = 11;// Render overlay to the right (increase to something like 100 to make it disappear, if we decide to use a custom selection indicator)
		}
	}
	
	private static void renderTextureAtIndex(double x, double y, int xIndex, int yIndex) {
		double rX = 0.25d * xIndex;
		double rY = 0.25d * yIndex;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();
		
		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + 16, 0).tex(rX, rY + 0.25d).endVertex();
		buff.pos(x + 16, y + 16, 0).tex(rX + 0.25d, rY + 0.25d).endVertex();
		buff.pos(x + 16, y, 0).tex(rX + 0.25d, rY).endVertex();
		buff.pos(x, y, 0).tex(rX, rY).endVertex();
		
		tessellator.draw();
	}
}
