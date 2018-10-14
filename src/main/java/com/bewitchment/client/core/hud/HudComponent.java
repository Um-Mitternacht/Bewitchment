package com.bewitchment.client.core.hud;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class HudComponent {
	
	protected int w, h;
	
	protected HudComponent(int width, int height) {
		this.w = width;
		this.h = height;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public abstract boolean isActive();
	public abstract void setHidden(boolean hidden);
	public abstract double getX();
	public abstract double getY();
	public abstract void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical);
	public abstract void resetConfig();	
	public abstract String getTooltip(int mouseX, int mouseY);
	public abstract void onClick(int mouseX, int mouseY);
	public abstract void render(ScaledResolution resolution, float partialTicks, boolean renderDummy);
	public abstract EnumHudAnchor getAnchorHorizontal();
	public abstract EnumHudAnchor getAnchorVertical();
	
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
