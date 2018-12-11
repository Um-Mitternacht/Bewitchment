package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelHellHound;
import com.bewitchment.common.entity.spirits.demons.EntityHellhound;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 10/9/2018.
 */
public class RenderHellhound extends RenderLiving<EntityHellhound> {

	private static final ResourceLocation[] textures = new ResourceLocation[6];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_4.png");
		textures[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_5.png");
		textures[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_6.png");
	}

	public RenderHellhound(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelHellHound(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHellhound entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityHellhound entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		} else {
			GlStateManager.scale(1.2d, 1.2d, 1.2d);
		}
	}

}

