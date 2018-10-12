package com.bewitchment.client.core.hud;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.bewitchment.client.gui.GuiEditMode;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HudController {

	public static final HudController INSTANCE = new HudController();
	
	public ArrayList<HudComponent> components = new ArrayList<>();
	public HudComponent grabbed = null;
	private boolean shouldShowHud = false;
	private int grabX = 0, grabY = 0;
	
	private HudController() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void tickClient(ClientTickEvent evt) {
		if (shouldShowHud) {
			shouldShowHud = false;
			Minecraft.getMinecraft().displayGuiScreen(new GuiEditMode());
		}
	}
	
	@SubscribeEvent
	public void renderEvent(RenderGameOverlayEvent evt) {
		if (evt.getType()==ElementType.HOTBAR && !isEditModeActive()) {
			render(evt.getResolution(), evt.getPartialTicks());
		}
	}
	
	@SubscribeEvent
	public void reloadConfig(ConfigChangedEvent evt) {
		if (evt.getModID().equals(LibMod.MOD_ID)) {
			components.forEach(c -> c.configChanged());
		}
	}
	
	@SubscribeEvent
	public void handleMouse(MouseEvent evt) {
		if (evt.getButton() != -1 && !isEditModeActive()) {
			onMouseInteraction(evt.getX(), evt.getY());
		}
	}

	public void render(ScaledResolution sr, float pticks) {
		for (HudComponent c:components) {
			if (c.isActive() || isEditModeActive()) {
				c.render(sr, pticks, isEditModeActive());
			}
			if (!c.isActive() && isEditModeActive()) {
				drawTranslucentRect(c.getX(), c.getY(), c.getWidth(), c.getHeight(), true);
			}
		}
		final int mouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
        final int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;

		if (isEditModeActive() && grabbed != null) {
			drawTranslucentRect(mouseX - grabX, mouseY - grabY, grabbed.w, grabbed.h, false);
		} else if (Minecraft.getMinecraft().currentScreen != null){
			HudComponent hud = getComponentAt(mouseX, mouseY, true);
			if (hud != null) {
				String ttip = hud.getTooltip(mouseX, mouseY);
				if (ttip != null && ttip.trim().length()>0) {
					GuiUtils.drawHoveringText(Arrays.asList(ttip), mouseX, mouseY, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth() / 2, Minecraft.getMinecraft().fontRenderer);
				}
			}
		}
	}
	
	public void onMouseInteraction(int clickX, int clickY) {
		if (isEditModeActive()) {
			if (grabbed != null) {
				grabbed.placedAt(clickX - grabX, clickY - grabY);
				grabbed.saveDataToConfig();
				grabbed = null;
			} else {
				HudComponent hud = getComponentAt(clickX, clickY, false);
				if (hud != null) {
					if (GuiScreen.isAltKeyDown()) {
						hud.resetConfig();
						hud.saveDataToConfig();
					} else if (GuiScreen.isShiftKeyDown()) {
						hud.setHidden(hud.isActive());
						hud.saveDataToConfig();
					} else {
						grabbed = hud;
						grabX = clickX - hud.getX();
						grabY = clickY - hud.getY();
					}
				}
			}
		} else {
			HudComponent hud = getComponentAt(clickX, clickY, true);
			if (hud != null) {
				hud.onClick(clickX, clickY);
			}
		}
	}

	@Nullable
	public HudComponent getComponentAt(int x, int y, boolean onlyActive) {
		for (HudComponent c:components) {
			if (!onlyActive || c.isActive()) {
				if (c.isHovered(x, y)) {
					return c;
				}
			}
		}
		return null;
	}
	
	public boolean isEditModeActive() {
		return Minecraft.getMinecraft().currentScreen instanceof GuiEditMode;
	}
	
	private static void drawTranslucentRect(int x, int y, int w, int h, boolean red) {
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();
		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
		if (red) {
			buff.pos(x, y + h, 0).color(0.8f, 0f, 0f, 0.4f).endVertex();
			buff.pos(x + w, y + h, 0).color(0.8f, 0f, 0f, 0.4f).endVertex();
			buff.pos(x + w, y, 0).color(0.8f, 0f, 0f, 0.4f).endVertex();
			buff.pos(x, y, 0).color(0.8f, 0f, 0f, 0.4f).endVertex();
		} else {
			buff.pos(x, y + h, 0).color(1f, 1f, 1f, 0.4f).endVertex();
			buff.pos(x + w, y + h, 0).color(1f, 1f, 1f, 0.4f).endVertex();
			buff.pos(x + w, y, 0).color(1f, 1f, 1f, 0.4f).endVertex();
			buff.pos(x, y, 0).color(1f, 1f, 1f, 0.4f).endVertex();
		}
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	public static void registerNewComponent(HudComponent hud) {
		INSTANCE.components.add(hud);
	}

	public void ungrab() {
		grabbed = null;
	}

	public void showHud() {
		this.shouldShowHud = true;
	}
}
