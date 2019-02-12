package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelDemon;
import com.bewitchment.common.entity.spirits.demons.EntityDemon;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 1/15/2019.
 */
public class RenderDemon extends RenderLiving<EntityDemon> {

	private static final ResourceLocation[] textures = new ResourceLocation[6];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demon_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demon_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demon_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demon_4.png");
		textures[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demon_5.png");
		textures[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/demon_6.png");
	}

	public RenderDemon(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelDemon(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDemon entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected boolean canRenderName(EntityDemon entity) {
		return true;
	}

	@Override
	protected void preRenderCallback(EntityDemon entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		} else {
			GlStateManager.scale(1.6d, 1.6d, 1.6d);
		}
	}

}
