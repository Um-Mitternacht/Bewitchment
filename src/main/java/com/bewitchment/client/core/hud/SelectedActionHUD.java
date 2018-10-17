package com.bewitchment.client.core.hud;

import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.client.core.event.ExtraBarButtonsHUD;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.core.handler.ConfigHandler;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import org.lwjgl.opengl.GL11;

public class SelectedActionHUD extends HudComponent {

	public SelectedActionHUD() {
		super(32, 32);
	}

	private static void renderTextureAtIndex(double x, double y, int width, int height, int xIndex, int yIndex) {
		double rX = 0.25d * xIndex;
		double rY = 0.25d * yIndex;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + height, 0).tex(rX, rY + 0.25d).endVertex();
		buff.pos(x + width, y + height, 0).tex(rX + 0.25d, rY + 0.25d).endVertex();
		buff.pos(x + width, y, 0).tex(rX + 0.25d, rY).endVertex();
		buff.pos(x, y, 0).tex(rX, rY).endVertex();

		tessellator.draw();
	}

	@Override
	public boolean isActive() {
		return !ConfigHandler.CLIENT.CURRENTACTION_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ConfigHandler.CLIENT.CURRENTACTION_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ConfigHandler.CLIENT.CURRENTACTION_HUD.h_anchor.dataToPixel(ConfigHandler.CLIENT.CURRENTACTION_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ConfigHandler.CLIENT.CURRENTACTION_HUD.v_anchor.dataToPixel(ConfigHandler.CLIENT.CURRENTACTION_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ConfigHandler.CLIENT.CURRENTACTION_HUD.v_anchor = vertical;
		ConfigHandler.CLIENT.CURRENTACTION_HUD.h_anchor = horizontal;
		ConfigHandler.CLIENT.CURRENTACTION_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ConfigHandler.CLIENT.CURRENTACTION_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public void resetConfig() {
		ConfigHandler.CLIENT.CURRENTACTION_HUD.v_anchor = EnumHudAnchor.START_RELATIVE;
		ConfigHandler.CLIENT.CURRENTACTION_HUD.h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ConfigHandler.CLIENT.CURRENTACTION_HUD.x = 0;
		ConfigHandler.CLIENT.CURRENTACTION_HUD.y = 0.25;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ConfigHandler.CLIENT.CURRENTACTION_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ConfigHandler.CLIENT.CURRENTACTION_HUD.v_anchor;
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public int getWidth() {
		return (int) (super.getWidth() * ConfigHandler.CLIENT.CURRENTACTION_HUD.scale);
	}

	@Override
	public int getHeight() {
		return (int) (super.getHeight() * ConfigHandler.CLIENT.CURRENTACTION_HUD.scale);
	}

	@Override
	public void onClick(int mouseX, int mouseY) {
	}

	@Override
	public void render(ScaledResolution resolution, float partialTicks, boolean renderDummy) {
		if (ExtraBarButtonsHUD.INSTANCE.isInExtraBar || renderDummy) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.color(1f, 1f, 1f, 0.5f);
			IHotbarAction sel = renderDummy ? ModAbilities.NIGHT_VISION : ExtraBarButtonsHUD.INSTANCE.actionScroller[0];
			Minecraft.getMinecraft().getTextureManager().bindTexture(sel.getIcon(player));
			renderTextureAtIndex(getX(), getY(), getWidth(), getHeight(), sel.getIconIndexX(player), sel.getIconIndexY(player));
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

}
