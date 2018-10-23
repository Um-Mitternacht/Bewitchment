package com.bewitchment.client.core.hud;

import com.bewitchment.client.core.hud.HudComponent.EnumHudAnchor;
import com.bewitchment.client.gui.GuiEditMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

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

	private static void drawTranslucentRect(double x, double y, int w, int h, boolean red) {
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

	@SubscribeEvent
	public void tickClient(ClientTickEvent evt) {
		if (shouldShowHud) {
			shouldShowHud = false;
			Minecraft.getMinecraft().displayGuiScreen(new GuiEditMode());
		}
	}

	@SubscribeEvent
	public void renderEvent(RenderGameOverlayEvent evt) {
		if (evt.getType() == ElementType.HOTBAR && !isEditModeActive()) {
			render(evt.getResolution(), evt.getPartialTicks());
		}
	}

	@SubscribeEvent
	public void handleMouse(MouseEvent evt) {
		if (evt.getButton() != -1 && !isEditModeActive()) {
			onMouseInteraction(evt.getX(), evt.getY());
		}
	}

	public void render(ScaledResolution sr, float pticks) {
		for (HudComponent c : components) {
			if (c.isActive() || isEditModeActive()) {
				c.render(sr, pticks, isEditModeActive());
			}
			if (isEditModeActive()) {
				drawAnchor((int) c.getX(), (int) c.getY(), c.getWidth(), c.getHeight(), true, c.getAnchorHorizontal(), c.getAnchorVertical());
				if (!c.isActive()) {
					drawTranslucentRect(c.getX(), c.getY(), c.getWidth(), c.getHeight(), true);
				}
			}
		}
		final int mouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
		final int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;

		if (isEditModeActive() && grabbed != null) {
			drawAnchor(mouseX - grabX, mouseY - grabY, grabbed.getWidth(), grabbed.getHeight(), false);
			drawTranslucentRect(mouseX - grabX, mouseY - grabY, grabbed.getWidth(), grabbed.getHeight(), false);
		} else if (Minecraft.getMinecraft().currentScreen != null) {
			HudComponent hud = getComponentAt(mouseX, mouseY, true);
			if (hud != null) {
				if (isEditModeActive()) {
					if (grabbed == null) {
						GuiUtils.drawHoveringText(Arrays.asList(
								I18n.format("gui.bwguicfg.pick"),
								I18n.format("gui.bwguicfg.orig"),
								I18n.format("gui.bwguicfg.hide")
						), mouseX, mouseY, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), Minecraft.getMinecraft().fontRenderer);
					}
				} else {
					String ttip = hud.getTooltip(mouseX, mouseY);
					if (ttip != null && ttip.trim().length() > 0) {
						GuiUtils.drawHoveringText(Arrays.asList(ttip), mouseX, mouseY, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth() / 2, Minecraft.getMinecraft().fontRenderer);
					}
				}
			}
		}
		GlStateManager.enableBlend();
	}

	private void drawAnchor(int i, int j, int w, int h, boolean fixed) {
		EnumHudAnchor hor = getHorizontalAlignmentForPoint(i, w);
		EnumHudAnchor ver = getVerticalAlignmentForPoint(j, h);
		drawAnchor(i, j, w, h, fixed, hor, ver);
	}

	private void drawAnchor(int i, int j, int w, int h, boolean fixed, EnumHudAnchor hor, EnumHudAnchor ver) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

		int sourceX = 0, sourceY = 0;

		if (hor == EnumHudAnchor.CENTER_ABSOLUTE || hor == EnumHudAnchor.CENTER_RELATIVE) {
			sourceX = sr.getScaledWidth() / 2;
			i += w / 2;
		} else if (hor == EnumHudAnchor.END_ABSOLUTE || hor == EnumHudAnchor.END_RELATIVE) {
			sourceX = sr.getScaledWidth();
			i += w;
		}

		if (ver == EnumHudAnchor.CENTER_ABSOLUTE || ver == EnumHudAnchor.CENTER_RELATIVE) {
			sourceY = sr.getScaledHeight() / 2;
			j += h / 2;
		} else if (ver == EnumHudAnchor.END_ABSOLUTE || ver == EnumHudAnchor.END_RELATIVE) {
			sourceY = sr.getScaledHeight();
			j += h;
		}

		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		GlStateManager.glLineWidth(3f);
		buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

		if (fixed) {
			buf.pos(sourceX, sourceY, 0).color(0f, 0.8f, 0f, 1f).endVertex();
			buf.pos(i, j, 0).color(0f, 0.8f, 0f, 1f).endVertex();
		} else {
			buf.pos(sourceX, sourceY, 0).color(0.8f, 0.8f, 0f, 0.7f).endVertex();
			buf.pos(i, j, 0).color(0.8f, 0.8f, 0f, 0.7f).endVertex();
		}

		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();

	}

	public void onMouseInteraction(int clickX, int clickY) {
		if (isEditModeActive()) {
			if (grabbed != null) {
				EnumHudAnchor hor = getHorizontalAlignmentForPoint(clickX - grabX, grabbed.getWidth());
				EnumHudAnchor ver = getVerticalAlignmentForPoint(clickY - grabY, grabbed.getHeight());
				grabbed.setRelativePosition(clickX - grabX, clickY - grabY, hor, ver);
				grabbed = null;
			} else {
				HudComponent hud = getComponentAt(clickX, clickY, false);
				if (hud != null) {
					if (GuiScreen.isAltKeyDown()) {
						hud.resetConfig();
					} else if (GuiScreen.isShiftKeyDown()) {
						hud.setHidden(hud.isActive());
					} else {
						grabbed = hud;
						grabX = (int) (clickX - hud.getX());
						grabY = (int) (clickY - hud.getY());
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

	private EnumHudAnchor getVerticalAlignmentForPoint(int y, int h) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		if (y + h < sr.getScaledHeight() * 1 / 3) {
			return EnumHudAnchor.START_ABSOULTE;
		}
		if (y > sr.getScaledHeight() * 2 / 3) {
			return EnumHudAnchor.END_ABSOLUTE;
		}
		return EnumHudAnchor.CENTER_ABSOLUTE;
	}

	private EnumHudAnchor getHorizontalAlignmentForPoint(int x, int w) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		if (x + w < sr.getScaledWidth() * 1 / 3) {
			return EnumHudAnchor.START_ABSOULTE;
		}
		if (x > sr.getScaledWidth() * 2 / 3) {
			return EnumHudAnchor.END_ABSOLUTE;
		}
		return EnumHudAnchor.CENTER_ABSOLUTE;
	}

	@Nullable
	public HudComponent getComponentAt(int x, int y, boolean onlyActive) {
		for (HudComponent c : components) {
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

	public void ungrab() {
		grabbed = null;
	}

	public void showHud() {
		this.shouldShowHud = true;
	}
}
