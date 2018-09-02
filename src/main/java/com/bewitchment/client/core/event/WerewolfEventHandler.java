package com.bewitchment.client.core.event;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.client.render.entity.model.ModelWerewolf;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class WerewolfEventHandler {

	private static final ResourceLocation MOON = new ResourceLocation(LibMod.MOD_ID, "textures/gui/moon_warning.png");
	private static final ResourceLocation WEREWOLF_SKIN = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/npcs/werewolf_grey_feral.png");
	private static final ModelWerewolf WW_MODEL = new ModelWerewolf();

	private static void renderTextureCenteredAt(double x, double y, int radius) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x - radius, y + radius, 0).tex(0, 1).endVertex();
		buff.pos(x + radius, y + radius, 0).tex(1, 1).endVertex();
		buff.pos(x + radius, y - radius, 0).tex(1, 0).endVertex();
		buff.pos(x - radius, y - radius, 0).tex(0, 0).endVertex();

		tessellator.draw();
	}

	@SubscribeEvent
	public void renderBar(RenderGameOverlayEvent.Post evt) {

		float minWarn = 12000;
		int transform = 12900;

		World world = Minecraft.getMinecraft().world;
		ITransformation t = Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null).getType();
		if (t == DefaultTransformations.WEREWOLF && evt.getType() == ElementType.HOTBAR && world.getMoonPhase() == 0) {
			GlStateManager.pushMatrix();
			Minecraft.getMinecraft().getTextureManager().bindTexture(MOON);
			int size = 8;
			int warn = 0;
			if (world.getWorldTime() < minWarn) {
				float state = 1f - ((minWarn - world.getWorldTime()) / (2 * minWarn));
				GlStateManager.disableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.color(1, 1, 1, state);
			} else if (world.getWorldTime() > transform) {
				GlStateManager.color(1, 1f, 1f, 1);
				warn = 2;
				size = 12;
			} else {
				float shade = 0.75f + 0.25f * (float) Math.sin(Math.PI * (Minecraft.getMinecraft().player.ticksExisted / 5f));
				GlStateManager.color(1, shade, shade, 1);
				warn = 1;
			}
			renderTextureCenteredAt(16, 16, size);
			if (Minecraft.getMinecraft().currentScreen != null) {
				int my = evt.getResolution().getScaledHeight() * 2 - Mouse.getY();
				if (0.5 * Mouse.getX() > 16 - size && 0.5 * Mouse.getX() < 16 + size && 0.5 * my > 16 - size && 0.5 * my < 16 + size) {
					GuiUtils.drawHoveringText(Arrays.asList(I18n.format("moon.tooltip." + warn)), Mouse.getX() / 2, my / 2, evt.getResolution().getScaledWidth(), evt.getResolution().getScaledHeight(), evt.getResolution().getScaledWidth() / 2, Minecraft.getMinecraft().fontRenderer);
				}
			}
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GlStateManager.popMatrix();
		}
	}

	@SubscribeEvent
	public void renderPlayer(RenderPlayerEvent.Pre evt) {
		EntityPlayer p = evt.getEntityPlayer();
		if (p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
			evt.setCanceled(true);
			GlStateManager.pushMatrix();
			Minecraft.getMinecraft().getTextureManager().bindTexture(WEREWOLF_SKIN);
			GL11.glRotated(-p.rotationYaw, 0, 1, 0);
			GL11.glRotated(0, 1, 0, 0);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glTranslated(0.0, -1.25, -0.2);
			GL11.glScaled(0.06, 0.06, 0.06);
			GlStateManager.disableLighting();
			WW_MODEL.render(p, p.limbSwing, p.limbSwingAmount / 3, p.ticksExisted, 0, p.rotationPitch, evt.getPartialRenderTick());
			GlStateManager.popMatrix();
		}
	}
}
