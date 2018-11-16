package com.bewitchment.client.core.event;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.client.render.entity.model.ModelWerewolf;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.content.transformation.werewolf.CapabilityWerewolfStatus;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WerewolfEventHandler {

	private static final ResourceLocation WEREWOLF_SKIN = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/npcs/werewolf_grey_feral.png");
	private static final ModelWerewolf WW_MODEL = new ModelWerewolf();

	@SubscribeEvent
	public void renderPlayer(RenderPlayerEvent.Pre evt) {
		EntityPlayer p = evt.getEntityPlayer();
		if (p.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
			if (p.getCapability(CapabilityWerewolfStatus.CAPABILITY, null).currentWWForm == 2) {
				evt.setCanceled(true);
				GlStateManager.pushMatrix();
				Minecraft.getMinecraft().getTextureManager().bindTexture(WEREWOLF_SKIN);
				GL11.glRotated(-p.rotationYaw, 0, 1, 0);
				GL11.glRotated(0, 1, 0, 0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glTranslated(0.0, -1.25, -0.2);
				GL11.glScaled(0.06, 0.06, 0.06);
				GlStateManager.disableLighting();
				WW_MODEL.render(p, p.limbSwing, p.limbSwingAmount / 3, p.ticksExisted, 0, p.rotationPitch, evt.getPartialRenderTick());
				GlStateManager.popMatrix();
			}
		}
	}
}
