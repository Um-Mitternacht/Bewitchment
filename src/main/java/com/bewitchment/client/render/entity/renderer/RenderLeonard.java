package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelLeonard;
import com.bewitchment.common.entity.spirits.demons.EntityLeonard;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 3/9/2019.
 */
public class RenderLeonard extends RenderLiving<EntityLeonard> {

	private static final ResourceLocation[] textures = new ResourceLocation[1];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/leonard.png");
	}

	public RenderLeonard(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelLeonard(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLeonard entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityLeonard entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		} else {
			GlStateManager.scale(1.2d, 1.2d, 1.2d);
		}
	}
}
