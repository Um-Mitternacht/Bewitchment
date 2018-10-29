package com.bewitchment.api.hotbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public interface IHotbarAction {

	public static final int ICONS_PER_ROW = 16;

	public ResourceLocation getName();

	@SideOnly(Side.CLIENT)
	public int getIconIndexX();

	@SideOnly(Side.CLIENT)
	public int getIconIndexY();

	@SideOnly(Side.CLIENT)
	public ResourceLocation getIcon();

	default void render(double d, double e, int width, int height, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.color(1f, 1f, 1f, alpha);
		Minecraft.getMinecraft().getTextureManager().bindTexture(getIcon());
		double inc = 1d / ICONS_PER_ROW;
		double rX = inc * getIconIndexX();
		double rY = inc * getIconIndexY();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(d, e + height, 0).tex(rX, rY + inc).endVertex();
		buff.pos(d + width, e + height, 0).tex(rX + inc, rY + inc).endVertex();
		buff.pos(d + width, e, 0).tex(rX + inc, rY).endVertex();
		buff.pos(d, e, 0).tex(rX, rY).endVertex();

		tessellator.draw();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
}
