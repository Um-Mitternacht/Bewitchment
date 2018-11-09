package com.bewitchment.client.core.hud;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.statics.ModConfig;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;

public class MoonHUD extends HudComponent {

	private static final float minWarn = 12000;
	private static final int transform = 12900;
	private static final ResourceLocation MOON = new ResourceLocation(LibMod.MOD_ID, "textures/gui/moon_warning.png");

	public MoonHUD() {
		super(24, 24);
	}

	@Override
	public void resetConfig() {
		ModConfig.CLIENT.MOON_HUD.v_anchor = EnumHudAnchor.START_ABSOULTE;
		ModConfig.CLIENT.MOON_HUD.h_anchor = EnumHudAnchor.START_ABSOULTE;
		ModConfig.CLIENT.MOON_HUD.x = 10;
		ModConfig.CLIENT.MOON_HUD.y = 10;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
		if (Minecraft.getMinecraft().world.getMoonPhase() == 0) {
			int warn = 0;
			if (Minecraft.getMinecraft().world.getWorldTime() < minWarn) {
			} else if (Minecraft.getMinecraft().world.getWorldTime() > transform) {
				warn = 2;
			} else {
				warn = 1;
			}
			return I18n.format("moon.tooltip." + warn);
		}
		return null;
	}

	@Override
	public void onClick(int mouseX, int mouseY) {

	}

	@Override
	public void render(ScaledResolution resolution, float partialTicks, boolean renderDummy) {
		World world = Minecraft.getMinecraft().world;
		ITransformation t = Minecraft.getMinecraft().player.getCapability(CapabilityTransformation.CAPABILITY, null).getType();
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(MOON);
		if (renderDummy) {
			GlStateManager.color(1, 1f, 1f, 1);
			renderTextureAt(getX(), getY(), 24, 24);
			GlStateManager.color(1f, 0.5f, 0.5f);
			renderTextureAt(getX(), getY(), 16, 16);
		} else if (t == DefaultTransformations.WEREWOLF && world.getMoonPhase() == 0) {
			if (world.getWorldTime() < minWarn) {
				float state = 1f - ((minWarn - world.getWorldTime()) / (2 * minWarn));
				GlStateManager.disableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.color(1, 1, 1, state);
			} else if (world.getWorldTime() > transform) {
				GlStateManager.color(1, 1f, 1f, 1);
			} else {
				float shade = 0.75f + 0.25f * (float) Math.sin(Math.PI * (Minecraft.getMinecraft().player.ticksExisted / 5f));
				GlStateManager.color(1, shade, shade, 1);
			}
			renderTextureAt(getX(), getY(), getWidth(), getHeight());
		}

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}

	@Override
	public int getWidth() {
		return getHeight();
	}

	@Override
	public int getHeight() {
		if (Minecraft.getMinecraft().world.getWorldTime() < transform) {
			return 16;
		}
		return 24;
	}

	@Override
	public boolean isActive() {
		return !ModConfig.CLIENT.MOON_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ModConfig.CLIENT.MOON_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.MOON_HUD.h_anchor.dataToPixel(ModConfig.CLIENT.MOON_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.MOON_HUD.v_anchor.dataToPixel(ModConfig.CLIENT.MOON_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ModConfig.CLIENT.MOON_HUD.v_anchor = vertical;
		ModConfig.CLIENT.MOON_HUD.h_anchor = horizontal;
		ModConfig.CLIENT.MOON_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ModConfig.CLIENT.MOON_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}


	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ModConfig.CLIENT.MOON_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ModConfig.CLIENT.MOON_HUD.v_anchor;
	}

}
