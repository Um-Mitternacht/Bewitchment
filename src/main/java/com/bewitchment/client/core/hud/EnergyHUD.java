package com.bewitchment.client.core.hud;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.mp.IMagicPowerUsingItem;
import com.bewitchment.client.ResourceLocations;
import com.bewitchment.common.core.statics.ModConfig;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * This class was created by Arekkuusu on 21/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SideOnly(Side.CLIENT)
public class EnergyHUD extends HudComponent {

	private int renderTime;
	private float visibilityLeft;
	private int oldEnergy = -1, oldMaxEnergy = -1;
	private ResourceLocation oldInfusion = null;
	private float barPulse;
	private boolean reversePulse;
	private boolean shouldPulse = false; // Only pulsate with white overlay after energy has changed
	private int lastPulsed = 40; // Prevents pulsating incontrollably when recharging fast enough. Min ticks between 2 pulsation

	public EnergyHUD() {
		super(25, 102);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null) {

			IMagicPowerContainer storage = Minecraft.getMinecraft().player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			if (lastPulsed > 0) {
				lastPulsed--;
			}
			boolean energyChanged = oldEnergy != storage.getAmount() || oldMaxEnergy != storage.getMaxAmount() || oldInfusion != BewitchmentAPI.getAPI().getPlayerInfusion(Minecraft.getMinecraft().player).getTexture();
			if (energyChanged) {
				shouldPulse = lastPulsed == 0;
			}
			if (energyChanged || isItemEnergyUsing() || !ModConfig.CLIENT.ENERGY_HUD.autoHide) {
				oldEnergy = storage.getAmount();
				oldMaxEnergy = storage.getMaxAmount();
				oldInfusion = BewitchmentAPI.getAPI().getPlayerInfusion(Minecraft.getMinecraft().player).getTexture();
				renderTime = 60;
				visibilityLeft = 1F;
			}

			if (renderTime > 0 && storage.getAmount() == storage.getMaxAmount()) {
				if (renderTime < 20) {
					visibilityLeft -= 0.05F;
					visibilityLeft = MathHelper.clamp(visibilityLeft, 0F, 1F);
				}

				renderTime--;
			}

			if (shouldPulse) {
				if (!reversePulse) {
					barPulse += 0.15F;
					if (barPulse > 1F) {
						barPulse = 1F;
						reversePulse = true;
					}
				} else {
					barPulse -= 0.15F;
					if (barPulse < 0F) {
						barPulse = 0;
						reversePulse = false;
						shouldPulse = false;
						lastPulsed = 40;
					}
				}
			}
		}
	}

	private boolean isItemEnergyUsing() { // Don't hide HUD when holding items that use ME/MP/AP
		EntityPlayer p = Minecraft.getMinecraft().player;
		if (p == null) {
			return false;
		}
		if (p.getHeldItemMainhand().hasCapability(IMagicPowerUsingItem.CAPABILITY, null)) {
			return true;
		}
		if (p.getHeldItemOffhand().hasCapability(IMagicPowerUsingItem.CAPABILITY, null)) {
			return true;
		}
		return HudController.INSTANCE.isEditModeActive();
	}

	@Override
	public void resetConfig() {
		ModConfig.CLIENT.ENERGY_HUD.v_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ModConfig.CLIENT.ENERGY_HUD.h_anchor = EnumHudAnchor.START_ABSOULTE;
		ModConfig.CLIENT.ENERGY_HUD.x = 10;
		ModConfig.CLIENT.ENERGY_HUD.y = 0;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
		if (renderTime > 0) {
			IMagicPowerContainer energy = Minecraft.getMinecraft().player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			return energy.getAmount() + "/" + energy.getMaxAmount();
		}
		return null;
	}

	@Override
	public void onClick(int mouseX, int mouseY) {
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


	@Override
	public void render(ScaledResolution resolution, float partialTicks, boolean renderDummy) {
		if (renderDummy) {
			float fll = (System.currentTimeMillis() % 3000) / 3000f;
			renderBarContent(fll);
			renderFrame(DefaultInfusions.NONE.getTexture());
			renderText(2000, 2000);
		} else if (renderTime > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 1);
			IMagicPowerContainer energy = Minecraft.getMinecraft().player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			double fill = getFillLevel(energy, partialTicks);
			renderBarContent(fill);
			renderPulse(fill);
			renderFrame(BewitchmentAPI.getAPI().getPlayerInfusion(Minecraft.getMinecraft().player).getTexture());
			renderText(energy.getAmount(), energy.getMaxAmount());
			GlStateManager.popMatrix();
		}
	}

	private void renderBarContent(double filled) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1f, 1f, 1f, visibilityLeft);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceLocations.ENERGY_BACKGROUND_FILL);
		renderTexture(getX() + 9, getY() + 14 + 74 * (1 - filled), 7, 74 * filled, 0, filled);
		GlStateManager.popMatrix();
	}

	private void renderPulse(double filled) {
		float alpha = this.barPulse * visibilityLeft;
		GlStateManager.pushMatrix();
		GlStateManager.color(1, 1, 1, alpha);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceLocations.ENERGY_BACKGROUND_PULSE);
		renderTexture(getX() + 9, getY() + 14 + 74 * (1 - filled), 7, 74 * filled, 0, filled);
		GlStateManager.popMatrix();
	}

	private void renderFrame(ResourceLocation texture) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1f, 1f, 1f, this.visibilityLeft);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		renderTexture(getX(), getY(), w, h, 0, 1);
		GlStateManager.popMatrix();
	}

	private void renderText(int amount, int maxAmount) {

	}

	private double getFillLevel(IMagicPowerContainer energy, float partialTicks) {
		double interpEnergy = 0;
		if (oldEnergy >= 0) {
			interpEnergy = (double) (energy.getAmount() - oldEnergy) * partialTicks + oldEnergy;
		} else {
			interpEnergy = energy.getAmount();
		}
		return interpEnergy / energy.getMaxAmount();
	}

	@Override
	public boolean isActive() {
		return !ModConfig.CLIENT.ENERGY_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ModConfig.CLIENT.ENERGY_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.ENERGY_HUD.h_anchor.dataToPixel(ModConfig.CLIENT.ENERGY_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.ENERGY_HUD.v_anchor.dataToPixel(ModConfig.CLIENT.ENERGY_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ModConfig.CLIENT.ENERGY_HUD.v_anchor = vertical;
		ModConfig.CLIENT.ENERGY_HUD.h_anchor = horizontal;
		ModConfig.CLIENT.ENERGY_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ModConfig.CLIENT.ENERGY_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ModConfig.CLIENT.ENERGY_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ModConfig.CLIENT.ENERGY_HUD.v_anchor;
	}

}
