package com.bewitchment.client.core.event;

import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.core.capability.energy.storage.CapabilityMagicPoints;
import com.bewitchment.common.core.capability.energy.user.CapabilityMagicPointsUser;
import com.bewitchment.common.core.handler.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
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

	//Todo: RENAME ME, NAME OVERLAPS WITH APPLIED ENERGISTICS, AND SOMETHING MORE CREATIVE COULD BE THOUGHT OF.

	private int renderTime;
	private float visible;
	private int oldEnergy = -1;
	private float barAlpha;
	private boolean reverse;
	private boolean shouldPulse = false; // Only pulsate with white overlay after energy has changed
	private int lastPulsed = 40; // Prevents pulsating incontrollably when recharging fast enough. Min ticks between 2 pulsation

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null) {
			Optional<CapabilityMagicPoints> optional = EnergyHandler.getEnergy(Minecraft.getMinecraft().player);

			if (optional.isPresent()) {
				CapabilityMagicPoints energy = optional.get();
				if (lastPulsed > 0)
					lastPulsed--;
				boolean energyChanged = (oldEnergy != energy.get());
				if (energyChanged)
					shouldPulse = lastPulsed == 0;

				if (energyChanged || isItemEnergyUsing()) {
					oldEnergy = energy.get();
					renderTime = 60;
					visible = 1F;
				}

				if (renderTime > 0 && energy.get() == energy.getMax()) {
					if (ConfigHandler.CLIENT.ENERGY_HUD.hide && renderTime < 20) {
						visible -= 0.05F;
						visible = MathHelper.clamp(visible, 0F, 1F);
					}

					renderTime--;
				}

				if (shouldPulse) {
					if (!reverse) {
						barAlpha += 0.15F;
						if (barAlpha > 1F) {
							barAlpha = 1F;
							reverse = true;
						}
					} else {
						barAlpha -= 0.15F;
						if (barAlpha < 0F) {
							barAlpha = 0;
							reverse = false;
							shouldPulse = false;
							lastPulsed = 40;
						}
					}
				}
			}
		}
	}

	private boolean isItemEnergyUsing() { // Don't hide HUD when holding items that use ME/MP/AP
		EntityPlayer p = Minecraft.getMinecraft().player;
		if (p == null)
			return false;
		if (p.getHeldItemMainhand().hasCapability(CapabilityMagicPointsUser.CAPABILITY, null)) {
			if (p.getHeldItemMainhand().getCapability(CapabilityMagicPointsUser.CAPABILITY, null).shouldShowHud())
				return true;
		}
		if (p.getHeldItemOffhand().hasCapability(CapabilityMagicPointsUser.CAPABILITY, null)) {
			if (p.getHeldItemOffhand().getCapability(CapabilityMagicPointsUser.CAPABILITY, null).shouldShowHud())
				return true;
		}
		return false;
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && renderTime > 0) {
			Minecraft mc = Minecraft.getMinecraft();
			TextureManager manager = mc.getTextureManager();
			Optional<CapabilityMagicPoints> optional = EnergyHandler.getEnergy(mc.player);

			if (optional.isPresent()) {
				CapabilityMagicPoints energy = optional.get();

				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();

				ScaledResolution resolution = event.getResolution();
				double interpEnergy = 0;
				if (oldEnergy >= 0) {
					interpEnergy = (double) (energy.get() - oldEnergy) * event.getPartialTicks() + oldEnergy;
				} else {
					interpEnergy = energy.get();
				}
				double filled = interpEnergy / energy.getMax();

				// System.out.println("fil: " + filled + ", chg: " + energy.get() + ", max: " + energy.getMax());

				int height = ConfigHandler.CLIENT.ENERGY_HUD.height;
				int width = ConfigHandler.CLIENT.ENERGY_HUD.width;
				int x = ConfigHandler.CLIENT.ENERGY_HUD.x;
				int y = resolution.getScaledHeight() - ConfigHandler.CLIENT.ENERGY_HUD.y;

				if (ConfigHandler.CLIENT.ENERGY_HUD.hide) {
					GlStateManager.color(1F, 1F, 1F, visible);
				}

				double barWidth = width * 7 / 25;

				GlStateManager.disableCull();
				manager.bindTexture(ResourceLocations.ENERGY_BACKGROUND[0]);
				renderTexture(x + 9, y + 88, barWidth, -(height - 28D) * filled, 0, filled);

				if (visible == 1f) {
					GlStateManager.pushMatrix();
					GlStateManager.color(1F, 1F, 1F, visible == 1F ? barAlpha : visible);

					manager.bindTexture(ResourceLocations.ENERGY_BACKGROUND[1]);
					renderTexture(x + 9, y + 88, barWidth, -(height - 28D) * filled, 0, filled);
					GlStateManager.enableCull();

					GlStateManager.popMatrix();
				}

				if (ConfigHandler.CLIENT.ENERGY_HUD.hide) {
					GlStateManager.color(1F, 1F, 1F, visible);
				}

				manager.bindTexture(energy.getType().getTexture());
				renderTexture(x, y, width, height, 0, 1);

				int textColor = 0x990066;
				if (ConfigHandler.CLIENT.ENERGY_HUD.hide) {
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
		buff.pos(x, y + height, 0).tex(0, vMax).endVertex();
		buff.pos(x + width, y + height, 0).tex(1, vMax).endVertex();
		buff.pos(x + width, y, 0).tex(1, vMin).endVertex();
		buff.pos(x, y, 0).tex(0, vMin).endVertex();

		tessellator.draw();
	}
}
