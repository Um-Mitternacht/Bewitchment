package com.bewitchment.client.core.hud;

import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.client.core.event.ExtraBarButtonsHUD;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.content.transformation.vampire.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.handler.ConfigHandler;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BloodViewerHUD extends HudComponent {
	
	public BloodViewerHUD() {
		super(ConfigHandler.CLIENT.BLOOD_HUD.x, ConfigHandler.CLIENT.BLOOD_HUD.y, 10, 14);
		active = ! ConfigHandler.CLIENT.BLOOD_HUD.deactivate;
	}

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_droplet.png");

	@Override
	public void resetConfig() {
		this.xpos = 0.5;
		this.ypos = 0.47;
		this.active = true;
	}

	@Override
	public void saveDataToConfig() {
		ConfigHandler.CLIENT.BLOOD_HUD.deactivate = !active;
		ConfigHandler.CLIENT.BLOOD_HUD.x = xpos;
		ConfigHandler.CLIENT.BLOOD_HUD.y = ypos;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
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
		if (renderDummy || (ExtraBarButtonsHUD.INSTANCE.actionScroller[0] == ModAbilities.DRAIN_BLOOD && ExtraBarButtonsHUD.INSTANCE.isInExtraBar && Minecraft.getMinecraft().pointedEntity instanceof EntityLivingBase)) {
			float filled = 1;
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
				renderTextureAt(getX(),getY(), w, h);
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public void placedAt(int x, int y) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		this.xpos = x / sr.getScaledWidth_double();
		this.ypos = y / sr.getScaledHeight_double();
	}

	@Override
	public void configChanged() {
		xpos = ConfigHandler.CLIENT.BLOOD_HUD.x;
		ypos = ConfigHandler.CLIENT.BLOOD_HUD.y;
		active = !ConfigHandler.CLIENT.BLOOD_HUD.deactivate;
	}
}
