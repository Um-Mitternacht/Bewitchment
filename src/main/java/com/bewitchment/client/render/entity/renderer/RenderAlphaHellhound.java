package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelHellHoundAlpha;
import com.bewitchment.common.entity.spirits.demons.EntityHellhoundAlpha;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 10/9/2018.
 */
public class RenderAlphaHellhound extends RenderLiving<EntityHellhoundAlpha> {

	private static final ResourceLocation[] textures = new ResourceLocation[6];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_4.png");
		textures[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_5.png");
		textures[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_6.png");
	}

	public RenderAlphaHellhound(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelHellHoundAlpha(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHellhoundAlpha entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityHellhoundAlpha entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.8d, 0.8d, 0.8d);
		} else {
			GlStateManager.scale(1.6d, 1.6d, 1.6d);
		}
	}

}

