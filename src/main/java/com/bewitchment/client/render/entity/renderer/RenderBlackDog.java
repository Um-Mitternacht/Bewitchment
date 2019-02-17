package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelBlackDog;
import com.bewitchment.common.entity.spirits.ghosts.EntityBlackDog;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 10/9/2018.
 */
public class RenderBlackDog extends RenderLiving<EntityBlackDog> {

	private static final ResourceLocation[] textures = new ResourceLocation[5];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/blackdog_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/blackdog_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/blackdog_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/blackdog_4.png");
		textures[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/blackdog_5.png");
	}

	public RenderBlackDog(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBlackDog(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlackDog entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntityBlackDog entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.9d, 0.9d, 0.9d);
		} else {
			GlStateManager.scale(1.8d, 1.8d, 1.8d);
		}
	}

}

