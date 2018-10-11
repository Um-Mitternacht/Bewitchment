package com.bewitchment.client.core.hud;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class HudComponent {
	
	protected int w, h;
	protected double xpos, ypos;
	protected boolean active = true;
	
	protected HudComponent(double posX, double posY, int width, int height) {
		this.xpos = posX;
		this.ypos = posY;
		this.w = width;
		this.h = height;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setHidden(boolean hidden) {
		active = !hidden;
	}
	
	public int getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return (int) (sr.getScaledWidth() * xpos);
	}

	public void setRelativeX(double x) {
		this.xpos = x;
	}

	public int getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return (int) (sr.getScaledHeight() * ypos);
	}

	public void setY(double y) {
		this.ypos = y;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
	
	public abstract void resetConfig();

	public abstract void saveDataToConfig();
	
	public void placedAt(int x, int y) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		this.xpos = x / sr.getScaledWidth_double();
		this.ypos = y / sr.getScaledHeight_double();
	}
	
	public abstract String getTooltip(int mouseX, int mouseY);
	
	public abstract void onClick(int mouseX, int mouseY);
	
	public abstract void render(ScaledResolution resolution, float partialTicks, boolean renderDummy);
	
	protected static void renderTextureAt(int x, int y, int w, int h) {
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
		if (getX() < mx && getX() + w > mx) {
			if (getY() < my && getY() + h > my) {
				return true;
			} 
		}
		return false;
	}
	
}
