package com.bewitchment.client.core.hud;

import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.content.transformation.vampire.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.handler.ConfigHandler;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BloodViewerHUD extends HudComponent {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_droplet.png");

	public BloodViewerHUD() {
		super(10, 14);
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public void onClick(int mouseX, int mouseY) {
	}

	@Override
	public void render(ScaledResolution sr, float partialTicks, boolean renderDummy) {
		if (renderDummy || (ExtraBarButtonsHUD.INSTANCE.actionScroller[0] == ModAbilities.DRAIN_BLOOD && ExtraBarButtonsHUD.INSTANCE.isInExtraBar() && Minecraft.getMinecraft().pointedEntity instanceof EntityLivingBase)) {
			float filled = (System.currentTimeMillis() % 3000) / 3000f;
			int maxBlood = 1;
			if (!renderDummy) {
				IBloodReserve ibr = Minecraft.getMinecraft().pointedEntity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
				filled = ibr.getPercentFilled();
				maxBlood = ibr.getMaxBlood();
			}
			if (maxBlood > 0) {
				GlStateManager.pushMatrix();
				GlStateManager.color(filled, filled, filled);
				GlStateManager.enableAlpha();
				Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
				renderTextureAt(getX(), getY(), w, h);
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean isActive() {
		return !ConfigHandler.CLIENT.BLOOD_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ConfigHandler.CLIENT.BLOOD_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ConfigHandler.CLIENT.BLOOD_HUD.h_anchor.dataToPixel(ConfigHandler.CLIENT.BLOOD_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ConfigHandler.CLIENT.BLOOD_HUD.v_anchor.dataToPixel(ConfigHandler.CLIENT.BLOOD_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ConfigHandler.CLIENT.BLOOD_HUD.v_anchor = vertical;
		ConfigHandler.CLIENT.BLOOD_HUD.h_anchor = horizontal;
		ConfigHandler.CLIENT.BLOOD_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ConfigHandler.CLIENT.BLOOD_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public void resetConfig() {
		ConfigHandler.CLIENT.BLOOD_HUD.v_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ConfigHandler.CLIENT.BLOOD_HUD.h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ConfigHandler.CLIENT.BLOOD_HUD.x = 15;
		ConfigHandler.CLIENT.BLOOD_HUD.y = 0;
		ConfigHandler.CLIENT.BLOOD_HUD.deactivate = false;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}


	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ConfigHandler.CLIENT.BLOOD_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ConfigHandler.CLIENT.BLOOD_HUD.v_anchor;
	}
}
