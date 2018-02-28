package com.bewitchment.client.core.event;

import com.bewitchment.api.capability.transformations.IBloodReserve;
import com.bewitchment.common.abilities.ModAbilities;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BloodViewerHUD {

	private static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/blood_droplet.png");

	private static void renderTextureAtIndex(double x, double y) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + 14, 0).tex(0, 1).endVertex();
		buff.pos(x + 10, y + 14, 0).tex(1, 1).endVertex();
		buff.pos(x + 10, y, 0).tex(1, 0).endVertex();
		buff.pos(x, y, 0).tex(0, 0).endVertex();

		tessellator.draw();
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && ExtraBarButtonsHUD.INSTANCE.actionScroller[0] == ModAbilities.DRAIN_BLOOD && ExtraBarButtonsHUD.INSTANCE.isInExtraBar && Minecraft.getMinecraft().pointedEntity instanceof EntityLivingBase) {
			IBloodReserve ibr = Minecraft.getMinecraft().pointedEntity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			float filled = ibr.getPercentFilled();
			if (filled > 0) {
				GlStateManager.pushMatrix();
				GlStateManager.color(filled, filled, filled);
				GlStateManager.enableAlpha();
				Minecraft mc = Minecraft.getMinecraft();
				ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
				mc.getTextureManager().bindTexture(TEXTURE);
				renderTextureAtIndex((sr.getScaledWidth() / 2) - 5d, (sr.getScaledHeight() / 2) - 30d);
				GlStateManager.popMatrix();
			}
		}
	}
}
