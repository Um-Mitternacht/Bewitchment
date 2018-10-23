package com.bewitchment.client.core.event;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.content.transformation.capability.ITransformationData;
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
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class VampireBloodBarHUD {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_meter.png");

	protected static void renderTextureAt(double x, double y, int w, int h, ITransformationData td) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();

		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		double maxBlood = 0.1d * td.getLevel();
		int dx = (int) (maxBlood * w);

		buf.pos(x + dx, y + h, 0).tex(maxBlood, 0.75).endVertex();
		buf.pos(x + w, y + h, 0).tex(1, 0.75).endVertex();
		buf.pos(x + w, y, 0).tex(1, 0.5).endVertex();
		buf.pos(x + dx, y, 0).tex(maxBlood, 0.5).endVertex();

		double bloodpc = maxBlood * td.getBlood() / td.getMaxBlood();
		double currentBlood = w * bloodpc;

		buf.pos(x, y + h, 0).tex(0, 0.5).endVertex();
		buf.pos(x + currentBlood, y + h, 0).tex(bloodpc, 0.5).endVertex();
		buf.pos(x + currentBlood, y, 0).tex(bloodpc, 0.25).endVertex();
		buf.pos(x, y, 0).tex(0, 0.25).endVertex();

		buf.pos(x, y + h, 0).tex(0, 0.25).endVertex();
		buf.pos(x + w, y + h, 0).tex(1, 0.25).endVertex();
		buf.pos(x + w, y, 0).tex(1, 0).endVertex();
		buf.pos(x, y, 0).tex(0, 0).endVertex();

		buf.pos(x - 10, y + 9, 0).tex(0, 1).endVertex();
		buf.pos(x - 3, y + 9, 0).tex(7f / 61f, 1).endVertex();
		buf.pos(x - 3, y, 0).tex(7f / 61f, 0.75).endVertex();
		buf.pos(x - 10, y, 0).tex(0, 0.75).endVertex();

		tessellator.draw();
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		ITransformationData td = Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && td.getType() == DefaultTransformations.VAMPIRE) {
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableAlpha();
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			mc.getTextureManager().bindTexture(TEXTURE);
			double textureStartX = (sr.getScaledWidth_double() / 2d) + 9d;
			double textureStartY = sr.getScaledHeight_double() - 39d;
			renderTextureAt(textureStartX, textureStartY, 71, 9, td);
//			renderTextureAt(0, 80, 305, 45, td);
			GlStateManager.popMatrix();
		}
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		if ((event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.AIR) && Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
			event.setCanceled(true);
		}
	}
}
