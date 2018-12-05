package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelSnake;
import com.bewitchment.common.entity.living.animals.EntitySnake;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 10/9/2018.
 */
public class RenderSnake extends RenderLiving<EntitySnake> {

	private static final ResourceLocation[] textures = new ResourceLocation[6];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/adder_1.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/adder_2.png");
		textures[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/adder_3.png");
		textures[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/adder_4.png");
		textures[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/adder_5.png");
		textures[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/adder_6.png");
	}

	public RenderSnake(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelSnake(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySnake entity) {
		return textures[entity.getSkinIndex()];
	}

	@Override
	protected void preRenderCallback(EntitySnake entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		if (entitylivingbaseIn.isChild()) {
			GlStateManager.scale(0.4d, 0.4d, 0.4d);
		} else {
			GlStateManager.scale(0.6d, 0.6d, 0.6d);
		}
	}

}

