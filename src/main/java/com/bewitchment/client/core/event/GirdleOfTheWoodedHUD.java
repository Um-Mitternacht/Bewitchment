package com.bewitchment.client.core.event;

import com.bewitchment.common.item.equipment.baubles.ItemGirdleOfTheWooded;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GirdleOfTheWoodedHUD {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/girdle_of_the_wooded_hud.png");
	private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");

	private static void renderTexture(double x, double y, double width, double height, double vMin, double vMax) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + height, 0).tex(0, vMax).endVertex();
		buff.pos(x + width, y + height, 0).tex(1, vMax).endVertex();
		buff.pos(x + width, y, 0).tex(1, vMin).endVertex();
		buff.pos(x, y, 0).tex(0, vMin).endVertex();

		tessellator.draw();
	}

	@SubscribeEvent
	public void onRenderHud(RenderGameOverlayEvent.Post evt) {
		if (evt.getType() == ElementType.ARMOR) {
			GL11.glPushMatrix();
			GlStateManager.enableAlpha();
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			int pieces = ItemGirdleOfTheWooded.getBarkPieces(Minecraft.getMinecraft().player);
			int dx = 8 * (10 - pieces) - 91 + evt.getResolution().getScaledWidth() / 2;
			for (int i = 0; i < pieces; i++) {
				renderTexture(dx + (8 * i), evt.getResolution().getScaledHeight() + 10 - GuiIngameForge.left_height, 9, 9, 0, 1);
			}
			Minecraft.getMinecraft().renderEngine.bindTexture(ICONS);
			GL11.glPopMatrix();
		}
	}

}
