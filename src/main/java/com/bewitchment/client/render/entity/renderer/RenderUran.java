package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelUran;
import com.bewitchment.common.entity.spirits.demons.EntityUran;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 10/9/2018.
 */
public class RenderUran extends RenderLiving<EntityUran> {

	private static final ResourceLocation[] textures = new ResourceLocation[9];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_4.png");
		textures[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_5.png");
		textures[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_6.png");
		textures[6] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_7.png");
		textures[7] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_8.png");
		textures[8] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/uran_9.png");
	}

	public RenderUran(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelUran(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityUran entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityUran entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		} else {
			GlStateManager.scale(1.6d, 1.6d, 1.6d);
		}
	}

}

