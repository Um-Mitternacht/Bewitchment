package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelLizard;
import com.bewitchment.common.entity.living.animals.EntityLizard;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLizard extends RenderLiving<EntityLizard> {

	private static final ResourceLocation[] textures = new ResourceLocation[4];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/lizard_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/lizard_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/lizard_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/lizard_4.png");
	}

	public RenderLizard(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelLizard(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLizard entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityLizard entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.4d, 0.4d, 0.4d);
		} else {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		}
	}

}
