package com.bewitchment.client.core.hud;

import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.core.statics.ModConfig;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;

public class SelectedActionHUD extends HudComponent {

	public SelectedActionHUD() {
		super(32, 32);
	}

	@Override
	public boolean isActive() {
		return !ModConfig.CLIENT.CURRENTACTION_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ModConfig.CLIENT.CURRENTACTION_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.CURRENTACTION_HUD.h_anchor.dataToPixel(ModConfig.CLIENT.CURRENTACTION_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.CURRENTACTION_HUD.v_anchor.dataToPixel(ModConfig.CLIENT.CURRENTACTION_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ModConfig.CLIENT.CURRENTACTION_HUD.v_anchor = vertical;
		ModConfig.CLIENT.CURRENTACTION_HUD.h_anchor = horizontal;
		ModConfig.CLIENT.CURRENTACTION_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ModConfig.CLIENT.CURRENTACTION_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public void resetConfig() {
		ModConfig.CLIENT.CURRENTACTION_HUD.v_anchor = EnumHudAnchor.START_RELATIVE;
		ModConfig.CLIENT.CURRENTACTION_HUD.h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ModConfig.CLIENT.CURRENTACTION_HUD.x = 0;
		ModConfig.CLIENT.CURRENTACTION_HUD.y = 0.25;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ModConfig.CLIENT.CURRENTACTION_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ModConfig.CLIENT.CURRENTACTION_HUD.v_anchor;
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public int getWidth() {
		return (int) (super.getWidth() * ModConfig.CLIENT.CURRENTACTION_HUD.scale);
	}

	@Override
	public int getHeight() {
		return (int) (super.getHeight() * ModConfig.CLIENT.CURRENTACTION_HUD.scale);
	}

	@Override
	public void onClick(int mouseX, int mouseY) {
	}

	@Override
	public void render(ScaledResolution resolution, float partialTicks, boolean renderDummy) {
		if (ExtraBarButtonsHUD.INSTANCE.isInExtraBar() || renderDummy) {
			GlStateManager.pushMatrix();
			IHotbarAction sel = renderDummy ? ModAbilities.NIGHT_VISION_VAMPIRE : ExtraBarButtonsHUD.INSTANCE.actionScroller[0];
			sel.render(getX(), getY(), getWidth(), getHeight(), 0.4f);
			GlStateManager.popMatrix();
		}
	}

}
