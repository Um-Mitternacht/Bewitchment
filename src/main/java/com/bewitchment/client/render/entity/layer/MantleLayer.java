package com.bewitchment.client.render.entity.layer;

import baubles.api.render.IRenderBauble;
import com.bewitchment.client.render.entity.model.ModelMantle;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MantleLayer implements LayerRenderer<AbstractClientPlayer> {

	private static final ModelMantle model = new ModelMantle();
	private static final MantleLayer INSTANCE = new MantleLayer();

	private MantleLayer() {
		//DON'T CALL THIS
	}

	public static MantleLayer getInstance() {
		return INSTANCE;
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		GL11.glPushMatrix();
		IRenderBauble.Helper.rotateIfSneaking(player); //Not being a bauble renderer, I'm not sure this works anymore
		GL11.glScaled(0.07, 0.07, 0.07);
		model.render(player, player.limbSwing, player.limbSwingAmount, ageInTicks + partialTicks, player.rotationYaw, player.rotationPitch, scale);
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldCombineTextures() {
		return false; //I have no clue what this means, try and set it to true if something about the texture is weird
	}

}
