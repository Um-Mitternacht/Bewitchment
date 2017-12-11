package com.bewitchment.client.core.event;

import com.bewitchment.api.capability.IEnergy;
import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.core.handler.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Optional;

/**
 * This class was created by Arekkuusu on 21/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SideOnly(Side.CLIENT)
public class EnergyHUD {

	private int renderTime;
	private float visible;
	private int oldEnergy;
	private float barAlpha;
	private boolean reverse;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null) {
			Optional<IEnergy> optional = EnergyHandler.getEnergy(Minecraft.getMinecraft().player);

			if (optional.isPresent()) {
				IEnergy energy = optional.get();

				if (oldEnergy != energy.get()) {
					oldEnergy = energy.get();
					renderTime = 60;
					visible = 1F;
				}

				if (renderTime > 0 && energy.get() == energy.getMax()) {
					if (ConfigHandler.ENERGY_HUD.hide && renderTime < 20) {
						visible -= 0.05F;
						visible = MathHelper.clamp(visible, 0F, 1F);
					}

					renderTime--;
				}

				if (!reverse) {
					barAlpha += 0.05F;
					if (barAlpha > 1F) {
						barAlpha = 1F;
						reverse = true;
					}
				} else {
					barAlpha -= 0.05F;
					if (barAlpha < 0F) {
						barAlpha = 0;
						reverse = false;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && renderTime > 0) {
			Minecraft mc = Minecraft.getMinecraft();
			TextureManager manager = mc.getTextureManager();
			Optional<IEnergy> optional = EnergyHandler.getEnergy(mc.player);

			if (optional.isPresent()) {
				IEnergy energy = optional.get();

				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();

				ScaledResolution resolution = event.getResolution();
				double filled = (double) energy.get() / (double) energy.getMax();
				int height = ConfigHandler.ENERGY_HUD.height;
				int width = ConfigHandler.ENERGY_HUD.width;
				int x = ConfigHandler.ENERGY_HUD.x;
				int y = resolution.getScaledHeight() - ConfigHandler.ENERGY_HUD.y;

				if (ConfigHandler.ENERGY_HUD.hide) {
					GlStateManager.color(1F, 1F, 1F, visible);
				}

				double off = 0.13725490196078431372549019607843D;

				GlStateManager.disableCull();
				manager.bindTexture(ResourceLocations.ENERGY_BACKGROUND[0]);
				renderTexture(x, y + 88, width, -((double) height - 28D) * filled, off, (1 - off) * filled);

				GlStateManager.pushMatrix();
				GlStateManager.color(1F, 1F, 1F, visible == 1F ? barAlpha : visible);

				manager.bindTexture(ResourceLocations.ENERGY_BACKGROUND[1]);
				renderTexture(x, y + 88, width, -((double) height - 28D) * filled, off, (1 - off) * filled);
				GlStateManager.enableCull();

				GlStateManager.popMatrix();

				if (ConfigHandler.ENERGY_HUD.hide) {
					GlStateManager.color(1F, 1F, 1F, visible);
				}

				manager.bindTexture(ResourceLocations.ENERGY);
				renderTexture(x, y, width, height, 0, 1);

				int textColor = 0x990066;
				if (ConfigHandler.ENERGY_HUD.hide) {
					int alpha = (int) (visible * 255);
					textColor = alpha << 24 | 0x990066;
				}

				String text = "E: " + energy.get();
				mc.fontRenderer.drawStringWithShadow(text, x, y - 10, textColor);
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}
		}
	}

	private void renderTexture(double x, double y, double width, double height, double vMin, double vMax) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + height, 0).tex((double) 0, vMax).endVertex();
		buff.pos(x + width, y + height, 0).tex((double) 1, vMax).endVertex();
		buff.pos(x + width, y, 0).tex((double) 1, vMin).endVertex();
		buff.pos(x, y, 0).tex((double) 0, vMin).endVertex();

		tessellator.draw();
	}
}
