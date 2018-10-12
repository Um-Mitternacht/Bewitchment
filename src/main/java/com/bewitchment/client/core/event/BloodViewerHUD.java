package com.bewitchment.client.core.event;

import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.client.core.hud.HudComponent;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.content.transformation.vampire.blood.CapabilityBloodReserve;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BloodViewerHUD extends HudComponent {
	
	//x and y are used as offset from the center
	public BloodViewerHUD() {
		super(0.51, 0.5, 10, 14);//TODO change with config values
	}

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_droplet.png");

	@Override
	public void resetConfig() {
		this.xpos = 0.51;
		this.ypos = 0.5;
	}

	@Override
	public void saveDataToConfig() {
		
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
		// TODO Auto-generated method stub
		
	}
}
