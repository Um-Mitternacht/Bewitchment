package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelOwl;
import com.bewitchment.common.entity.living.animals.EntityOwl;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderOwl extends RenderLiving<EntityOwl> {

	private static final ResourceLocation[] textures = new ResourceLocation[4];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_0.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_1.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_2.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_3.png");
	}

	public RenderOwl(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelOwl(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityOwl entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityOwl entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.4d, 0.4d, 0.4d);
		} else {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		}
	}

}
