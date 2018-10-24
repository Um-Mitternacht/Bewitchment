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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VampireBloodBarHUD {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_meter.png");

	protected static void renderTextureAt(double x, double y, int w, int h, ITransformationData td) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		
		buf.getClass();//TODO RENDER HERE

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
