package com.bewitchment.client.core.hud;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.content.transformation.vampire.CapabilityVampire;
import com.bewitchment.common.core.statics.ModConfig;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class VampireBloodBarHUD extends HudComponent {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_meter.png");

	public VampireBloodBarHUD() {
		super(80, 8);
		MinecraftForge.EVENT_BUS.register(this);
	}

	protected static void renderTextureAt(double x, double y, int w, int h, double uMin, double vMin, double uMax, double vMax) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + h, 0).tex(uMin, vMax).endVertex();
		buff.pos(x + w, y + h, 0).tex(uMax, vMax).endVertex();
		buff.pos(x + w, y, 0).tex(uMax, vMin).endVertex();
		buff.pos(x, y, 0).tex(uMin, vMin).endVertex();

		tessellator.draw();
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Pre event) {
		if ((event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.AIR) && Minecraft.getMinecraft().player.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
			event.setCanceled(true);
		}
	}

	@Override
	public boolean isActive() {
		return !ModConfig.CLIENT.VAMPIRE_METER_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ModConfig.CLIENT.VAMPIRE_METER_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.VAMPIRE_METER_HUD.h_anchor.dataToPixel(ModConfig.CLIENT.VAMPIRE_METER_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.VAMPIRE_METER_HUD.v_anchor.dataToPixel(ModConfig.CLIENT.VAMPIRE_METER_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ModConfig.CLIENT.VAMPIRE_METER_HUD.v_anchor = vertical;
		ModConfig.CLIENT.VAMPIRE_METER_HUD.h_anchor = horizontal;
		ModConfig.CLIENT.VAMPIRE_METER_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ModConfig.CLIENT.VAMPIRE_METER_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public void resetConfig() {
		ModConfig.CLIENT.VAMPIRE_METER_HUD.v_anchor = EnumHudAnchor.END_ABSOLUTE;
		ModConfig.CLIENT.VAMPIRE_METER_HUD.h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ModConfig.CLIENT.VAMPIRE_METER_HUD.x = 9 + getWidth() / 2;
		ModConfig.CLIENT.VAMPIRE_METER_HUD.y = 39 - getHeight();
		ModConfig.CLIENT.VAMPIRE_METER_HUD.deactivate = false;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ModConfig.CLIENT.VAMPIRE_METER_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ModConfig.CLIENT.VAMPIRE_METER_HUD.v_anchor;
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
//		ITransformationData data = Minecraft.getMinecraft().player.getCapability(CapabilityTransformationData.CAPABILITY, null);
//		if (data.getType() == DefaultTransformations.VAMPIRE) {
//			return data.getBlood()+"/"+data.getMaxBlood();
//		}
		return null;
	}

	@Override
	public void onClick(int mouseX, int mouseY) {
	}

	@Override
	public void render(ScaledResolution resolution, float partialTicks, boolean renderDummy) {
		int level = 5;
		double blood = 0;
		boolean doRender = false;
		if (renderDummy) {
			blood = (System.currentTimeMillis() % 3000) / 3000d;
			doRender = true;
		} else {
			CapabilityTransformation t_data = Minecraft.getMinecraft().player.getCapability(CapabilityTransformation.CAPABILITY, null);
			CapabilityVampire v_data = Minecraft.getMinecraft().player.getCapability(CapabilityVampire.CAPABILITY, null);
			if (t_data.getType() == DefaultTransformations.VAMPIRE) {
				doRender = true;
				blood = v_data.getBlood() / (double) v_data.getMaxBlood(Minecraft.getMinecraft().player);
				level = t_data.getLevel();
			}
		}
		if (doRender) {
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			GlStateManager.enableBlend();

			renderTextureAt(getX() + 1, getY(), (int) ((getWidth() - 2) * blood), getHeight(), 0, 0.5, blood, 1); //Draw the content
			renderTextureAt(getX(), getY(), 1, getHeight(), 4d / getWidth(), 0, 5d / getWidth(), 0.5); //Draw the first vertical line
			renderTextureAt(getX() + getWidth() - 2, getY(), 2, getHeight(), 3d / getWidth(), 0, 5d / getWidth(), 0.5); //Draw the last 2 vertical lines
			int pixelsPerSlot = (int) ((getWidth() - 2d) / (level + 1d));
			for (int i = 0; i < level; i++) { //Draw each slot
				renderTextureAt(1 + getX() + i * pixelsPerSlot, getY(), 1, getHeight(), 3d / getWidth(), 0, 4d / getWidth(), 0.5);
				renderTextureAt(2 + getX() + i * pixelsPerSlot, getY(), pixelsPerSlot - 1, getHeight(), 0, 0, 1d / getWidth(), 0.5);
			}
			renderTextureAt(1 + getX() + level * pixelsPerSlot, getY(), 1, getHeight(), 3d / getWidth(), 0, 4d / getWidth(), 0.5);
			renderTextureAt(2 + getX() + level * pixelsPerSlot, getY(), getWidth() - (pixelsPerSlot * level) - 4, getHeight(), 0, 0, 1d / getWidth(), 0.5);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

	@Override
	public int getWidth() {
		return 80;
	}

	@Override
	public int getHeight() {
		return 8;
	}

}
