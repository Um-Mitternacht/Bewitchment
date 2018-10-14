package com.bewitchment.client.core.hud;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
/**
 * @author zabi94
 */
public abstract class HudComponent {

	protected int w, h;

	/**
	 * This class represents hud components for the mod.
	 * A single instance should be created per hud component
	 * <p>
	 * All components should be registered once during startup on the client side via HudController.registerNewComponent(HudComponent)
	 *
	 * @see HudController#registerNewComponent(HudComponent)
	 */
	protected HudComponent(int width, int height) {
		this.w = width;
		this.h = height;
	}

	protected static void renderTextureAt(double x, double y, int w, int h) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + h, 0).tex(0, 1).endVertex();
		buff.pos(x + w, y + h, 0).tex(1, 1).endVertex();
		buff.pos(x + w, y, 0).tex(1, 0).endVertex();
		buff.pos(x, y, 0).tex(0, 0).endVertex();

		tessellator.draw();
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	/**
	 * This method should read from a permanent client data storage, eg: configs
	 */
	public abstract boolean isActive();

	/**
	 * This method should write to a permanent client data storage, eg: configs
	 */
	public abstract void setHidden(boolean hidden);

	/**
	 * This method should read from a permanent client data storage, eg: configs
	 */
	public abstract double getX();

	/**
	 * This method should read from a permanent client data storage, eg: configs
	 */
	public abstract double getY();

	/**
	 * This method should write to a permanent client data storage, eg: configs
	 */
	public abstract void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical);

	/**
	 * This method should write to a permanent client data storage, eg: configs
	 */
	public abstract void resetConfig();

	/**
	 * This method should read from a permanent client data storage, eg: configs
	 */
	public abstract EnumHudAnchor getAnchorHorizontal();

	/**
	 * This method should read from a permanent client data storage, eg: configs
	 */
	public abstract EnumHudAnchor getAnchorVertical();

	/**
	 * This only gets called when the cursor is hovering on the hud element
	 *
	 * @param mouseX is given only to provide a way to give different tooltips depending on the cursor position inside the hud
	 * @param mouseY is given only to provide a way to give different tooltips depending on the cursor position inside the hud
	 * @return A string to be displayed as tooltip, or a null or empty string to not show anything
	 */
	public abstract String getTooltip(int mouseX, int mouseY);

	/**
	 * This only gets called when the cursor is hovering on the hud element and a mouse button is pressed
	 *
	 * @param mouseX is given only to provide a way to act differently depending on the cursor position inside the hud
	 * @param mouseY is given only to provide a way to act differently depending on the cursor position inside the hud
	 */
	public abstract void onClick(int mouseX, int mouseY);

	/**
	 * Actually do the rendering on the screen. Anything drawn should always be inside the rectangle described by
	 * {@link #getX()}, {@link #getY()}, {@link #getX()}+{@link #getWidth()}, {@link #getY()}+{@link #getHeight()}.
	 * <br><br>If the element is not active when using {@link #isActive()}, this method won't be called at all, except
	 * for dummy renders
	 *
	 * @param resolution   The current resolution of the minecraft window
	 * @param partialTicks A float representing half ticks progress
	 * @param renderDummy  if true, the hud should display sample data that causes it to be visible, and not real data. This is used by the bwguicfg command to show all huds even when they are not active
	 */
	public abstract void render(ScaledResolution resolution, float partialTicks, boolean renderDummy);

	public boolean isHovered(int mx, int my) {
		if (getX() < mx && getX() + getWidth() > mx) {
			if (getY() < my && getY() + getHeight() > my) {
				return true;
			}
		}
		return false;
	}

	public static enum EnumHudAnchor {

		START_RELATIVE(new AnchorHelper.RelativeVersion(new AnchorHelper.AbsoluteStartHelper())),
		CENTER_RELATIVE(new AnchorHelper.RelativeVersion(new AnchorHelper.AbsoluteCenterHelper())),
		END_RELATIVE(new AnchorHelper.RelativeVersion(new AnchorHelper.AbsoluteEndHelper())),
		START_ABSOULTE(new AnchorHelper.AbsoluteStartHelper()),
		CENTER_ABSOLUTE(new AnchorHelper.AbsoluteCenterHelper()),
		END_ABSOLUTE(new AnchorHelper.AbsoluteEndHelper());

		private AnchorHelper ah = null;

		private EnumHudAnchor(AnchorHelper helper) {
			ah = helper;
		}

		public double dataToPixel(double data, int componentSize, int screeScaledSize) {
			return ah.getPixel(data, screeScaledSize, componentSize);
		}

		public double pixelToData(double pixel, int componentSize, int screeScaledSize) {
			return ah.getData(pixel, screeScaledSize, componentSize);
		}
	}

}
