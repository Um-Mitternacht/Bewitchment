package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelDemoness;
import com.bewitchment.common.entity.spirits.demons.EntityDemoness;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 1/15/2019.
 */
public class RenderDemoness extends RenderLiving<EntityDemoness> {

	private static final ResourceLocation[] textures = new ResourceLocation[4];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demoness_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demoness_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demoness_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demoness_4.png");
	}

	public RenderDemoness(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelDemoness(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDemoness entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityDemoness entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		} else {
			GlStateManager.scale(1.6d, 1.6d, 1.6d);
		}
	}

}
