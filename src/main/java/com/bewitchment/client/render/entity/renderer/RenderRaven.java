package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelRaven;
import com.bewitchment.common.entity.living.animals.EntityRaven;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRaven extends RenderLiving<EntityRaven> {

	private static final ResourceLocation[] textures = new ResourceLocation[1];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/raven.png");
	}

	public RenderRaven(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelRaven(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRaven entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityRaven entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.4d, 0.4d, 0.4d);
		} else {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		}
	}

}
