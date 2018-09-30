package com.bewitchment.client.render.entity.layer;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import com.bewitchment.client.render.entity.model.ModelMantle;
import com.bewitchment.common.item.ModItems;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MantleLayer implements LayerRenderer<AbstractClientPlayer> {

	private ModelMantle model;

	public MantleLayer(ModelPlayer playerModel) {
		model = new ModelMantle(playerModel);
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.BODY.getValidSlots()[0]).getItem() == ModItems.mantle) {
			GL11.glPushMatrix();
			IRenderBauble.Helper.rotateIfSneaking(player); //Not being a bauble renderer, I'm not sure this works anymore
			GL11.glScaled(0.066, 0.067, 0.068);
			GL11.glTranslated(0, -0.5, 0);
			model.render(player, player.limbSwing, player.limbSwingAmount, ageInTicks + partialTicks, player.rotationYaw, player.rotationPitch, scale);
			GL11.glPopMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false; //I have no clue what this means, try and set it to true if something about the texture is weird
	}

}
