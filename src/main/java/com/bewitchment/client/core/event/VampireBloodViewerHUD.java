package com.bewitchment.client.core.event;

import org.lwjgl.opengl.GL11;

import com.bewitchment.api.capability.EnumTransformationType;
import com.bewitchment.api.capability.ITransformationData;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VampireBloodViewerHUD {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_meter.png");

	private static void renderTextureAtTile(double x, double y, int filled, int available, double lasth, double lasta) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();
		
		for (int i = 0; i < filled; i++) {
			
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buff.pos(x + i * 8, y + 9, 0).tex(0, 0.5).endVertex();
			buff.pos(x + (i * 8) + 7, y + 9, 0).tex(1, 0.5).endVertex();
			buff.pos(x + (i * 8) + 7, y, 0).tex(1, 0).endVertex();
			buff.pos(x + i * 8, y, 0).tex(0, 0).endVertex();
			
			tessellator.draw();
		}
		
		for (int i = filled; i < available; i++) {
			
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buff.pos(x + i * 8, y + 9, 0).tex(0, 1).endVertex();
			buff.pos(x + (i * 8) + 7, y + 9, 0).tex(1, 1).endVertex();
			buff.pos(x + (i * 8) + 7, y, 0).tex(1, 0.5).endVertex();
			buff.pos(x + i * 8, y, 0).tex(0, 0.5).endVertex();
			
			tessellator.draw();
		}
		if (lasta > 0) {
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buff.pos(x + filled * 8, y + 9, 0).tex(0, 1).endVertex();
			buff.pos(x + (filled * 8) + (7 * lasta), y + 9, 0).tex(lasta, 1).endVertex();
			buff.pos(x + (filled * 8) + (7 * lasta), y, 0).tex(lasta, 0.5).endVertex();
			buff.pos(x + filled * 8, y, 0).tex(0, 0.5).endVertex();
			tessellator.draw();
		}
		
		if (lasth > 0) {
			
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buff.pos(x + filled * 8, y + 9, 0).tex(0, 0.5).endVertex();
			buff.pos(x + (filled * 8) + (7 * lasth), y + 9, 0).tex(lasth, 0.5).endVertex();
			buff.pos(x + (filled * 8) + (7 * lasth), y, 0).tex(lasth, 0).endVertex();
			buff.pos(x + filled * 8, y, 0).tex(0, 0).endVertex();
			
			tessellator.draw();
			
		}
		
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		ITransformationData td = Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && td.getType() == EnumTransformationType.VAMPIRE) {
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableAlpha();
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			mc.getTextureManager().bindTexture(TEXTURE);
			int totalHearts = td.getMaxBlood() / 80;
			int fullHearts = td.getBlood() / 80;
			double halfHeart = (td.getBlood() % 80) / 80d;
			double halfTotalHeart = (td.getMaxBlood() % 80) / 80d;
			halfHeart = roundToThirds(halfHeart);
			halfTotalHeart = roundToThirds(halfTotalHeart);
			renderTextureAtTile((sr.getScaledWidth() / 2) + 9d, sr.getScaledHeight() - 39d, fullHearts, totalHearts, halfHeart, halfTotalHeart);
			GlStateManager.popMatrix();
		}
	}
	
	private double roundToThirds(double val) {
		if (val > 0.66)
			return 0.66;
		if (val > 0.33)
			return 0.33;
		return 0;
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD && Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == EnumTransformationType.VAMPIRE) {
			event.setCanceled(true);
		}
	}
}
