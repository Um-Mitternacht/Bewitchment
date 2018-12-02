package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelBlindworm;
import com.bewitchment.common.entity.living.animals.EntityBlindworm;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBlindworm extends RenderLiving<EntityBlindworm> {

	private static final ResourceLocation texture = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/blindworm.png");


	public RenderBlindworm(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBlindworm(), 0.1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlindworm entity) {
		return texture;
	}

	@Override
	protected void preRenderCallback(EntityBlindworm entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.4d, 0.4d, 0.4d);
		} else {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		}
	}

}
